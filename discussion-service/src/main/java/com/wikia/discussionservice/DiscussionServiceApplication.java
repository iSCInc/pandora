package com.wikia.discussionservice;

import com.codahale.metrics.health.HealthCheck;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.wikia.discussionservice.configuration.DiscussionServiceConfiguration;

import com.wikia.discussionservice.spring.SpringContextLoaderListener;
import io.dropwizard.Application;
import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.ws.rs.Path;
import java.util.Map;

public class DiscussionServiceApplication extends Application<DiscussionServiceConfiguration> {

  public static void main(String[] args) throws Exception {
    new DiscussionServiceApplication().run(args);
  }

  @Override
  public String getName() {
    return "discussion-service"; // no uppercase letters
  }


  @Override
  public void initialize(Bootstrap<DiscussionServiceConfiguration> bootstrap) {
//    bootstrap.addBundle(new ConsulBundle<ExampleConfiguration>() {
//      @Override
//      protected ConsulConfig narrowConfig(ExampleConfiguration config) {
//        return config.getConsulConfig();
//      }
//    });
  }

  @Override
  public void run(DiscussionServiceConfiguration configuration, Environment environment)
      throws Exception {

      // init Spring context
      // before we init the app context, we have to create a parent context with all
      // the config objects others rely on to get initialized
      AnnotationConfigWebApplicationContext parent = new AnnotationConfigWebApplicationContext();
      AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

      parent.refresh();
      parent.getBeanFactory().registerSingleton("configuration", configuration);
      parent.registerShutdownHook();
      parent.start();

      // the real main app context has a link to the parent context
      ctx.setParent(parent);
      ctx.register(DiscussionServiceSpringConfiguration.class);
      ctx.refresh();
      ctx.registerShutdownHook();
      ctx.start();

      // now that Spring is started, let's get all the beans that matter into DropWizard

      //health checks
      Map<String, HealthCheck> healthChecks = ctx.getBeansOfType(HealthCheck.class);
      for(Map.Entry<String, HealthCheck> entry : healthChecks.entrySet()) {
        environment.healthChecks().register(entry.getKey(), entry.getValue());
      }

      //resources
      Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);
      for(Map.Entry<String,Object> entry : resources.entrySet()) {
        environment.jersey().register(entry.getValue());
      }

      //tasks
      Map<String, Task> tasks = ctx.getBeansOfType(Task.class);
      for(Map.Entry<String,Task> entry : tasks.entrySet()) {
        environment.admin().addTask(entry.getValue());
      }

      // last, but not least, let's link Spring to the embedded Jetty in Dropwizard
      environment.servlets().addServletListeners(new SpringContextLoaderListener(ctx));

      // optional
      environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
