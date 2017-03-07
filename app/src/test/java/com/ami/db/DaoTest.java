package com.ami.db;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import com.codahale.metrics.MetricRegistry;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * Ref :- https://gist.github.com/yunspace/9a50e11dbd8661271220.
 * @author: Amit Khandelwal
 * Date: 3/4/17
 */
public class DaoTest {
	public static Environment env;
	public static DBI dbi;
	private Handle handle;
	private Liquibase liquibase;

	@Before
	public void setup() throws IOException, LiquibaseException {
		env = new Environment( "test-env", Jackson.newObjectMapper(), null, new
				MetricRegistry(),
				null );
		dbi = new DBIFactory().build( env, getDataSourceFactory(), "test" );
		handle = dbi.open();
		migrateDatabase();
	}

	static DataSourceFactory getDataSourceFactory()
	{
		DataSourceFactory dataSourceFactory = new DataSourceFactory();
		dataSourceFactory.setDriverClass( "org.h2.Driver" );
		dataSourceFactory.setUrl( "jdbc:h2:mem:restbest" );
		dataSourceFactory.setUser( "sa" );
		dataSourceFactory.setPassword( "" );
		return dataSourceFactory;
	}

	public static DBI getDbi() {
		return dbi;
	}

	public static Environment getEnvironment() {
		return env;
	}

	@After
	public void tearDown() {
		try {
			liquibase.dropAll();
		} catch (Exception e) {
			throw new RuntimeException("failed clearing up Liquibase object", e);
		}
		handle.close();
	}

	private void migrateDatabase() throws LiquibaseException {
		liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(handle.getConnection()));
		liquibase.update("");
	}
}
