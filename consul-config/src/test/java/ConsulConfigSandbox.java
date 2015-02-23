import com.wikia.gradle.ConsulAppConfig;

public class ConsulConfigSandbox {
  public static void main(String[] args) {
    String prod = "consul.service.sjc.consul";
    String dev = "dev-consul";
    String current = prod;
    new ConsulAppConfig(current, 8500).getConfig("mobile-config", "prod");
  }
}
