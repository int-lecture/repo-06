package loginServer;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

//Controlliert der Zugang zu Resoursen
@Provider
public class CORS implements ContainerResponseFilter {

   @Override
   public ContainerResponse filter(ContainerRequest requestContext, ContainerResponse cres)  {
      cres.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
      cres.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
      cres.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
      cres.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      cres.getHttpHeaders().add("Access-Control-Max-Age", "1209600");

      return cres;
   }


}