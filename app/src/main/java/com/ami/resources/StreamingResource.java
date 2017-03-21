package com.ami.resources;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 * @author: Amit Khandelwal
 * Date: 2/4/17
 */


@Path("/model/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StreamingResource {

	// Get product using streaming
	@GET
	@Path("{id}")
	public Response getProduct(@PathParam("id") String id) throws Exception {
			StreamingOutput stream = new StreamingOutput() {
				@Override
				public void write(OutputStream output) throws IOException, WebApplicationException {
					Writer writer = new BufferedWriter( new OutputStreamWriter(output));
					writer.write("amit khandelwal");
					writer.flush();
				}
			};
			return Response.ok(stream).build();
	}
}
