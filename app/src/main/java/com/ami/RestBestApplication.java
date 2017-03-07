package com.ami;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.skife.jdbi.v2.DBI;

import com.ami.db.ProductDao;
import com.ami.filters.LoggingFilter;
import com.ami.resources.ProductResource;
import com.ami.resources.StreamingResource;
import com.ami.services.ProductService;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class RestBestApplication extends Application<RestBestConfiguration> {

    public static void main(final String[] args) throws Exception {
        new RestBestApplication().run(args);
    }

    @Override
    public String getName() {
        return "restbest";
    }

    @Override
    public void initialize(final Bootstrap<RestBestConfiguration> bootstrap) {
        // TODO :- enable db migrations
       /* bootstrap.addBundle(new MigrationsBundle<RestBestConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(RestBestConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });*/
    }

    @Override
    public void run(final RestBestConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        final StreamingResource streamingResource = new StreamingResource();
        environment.jersey().register(streamingResource);
        environment.servlets().addFilter("LoggingFilter", new LoggingFilter()).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");;

        // DB
        DBIFactory factory = new DBIFactory();
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        ProductDao productDao = jdbi.onDemand(ProductDao.class);

        // Services
        final ProductService productService = new ProductService(productDao);

        // resources
        environment.jersey().register(new ProductResource(productService));
    }

}
