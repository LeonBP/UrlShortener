package urlshortener.RedPepper.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Config
 */
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

public class Config   {
  @JsonProperty("mode")
  private Integer mode = null;

  @JsonProperty("clientIP")
  private Integer clientIP = null;

  @JsonProperty("precision")
  private Integer precision = null;

  public String getUrl() {

    return url;
  }

  public void setUrl(String url) {

    this.url = url;
  }

  @JsonProperty("url")
  private String url = null;
  public Config mode(Integer mode) {
    this.mode = mode;
    return this;
  }

   /**
   * Get mode
   * @return mode
  **/
  @ApiModelProperty(value = "")


  public Integer getMode() {

    return mode;
  }

  public void setMode(Integer mode) {

    this.mode = mode;
  }

  public Config clientIP(Integer clientIP) {
    this.clientIP = clientIP;
    return this;
  }

   /**
   * Get clientIP
   * @return clientIP
  **/
  @ApiModelProperty(value = "")


  public Integer getClientIP() {
    return clientIP;
  }

  public void setClientIP(Integer clientIP) {

    this.clientIP = clientIP;
  }

  public Config precision(Integer precision) {
    this.precision = precision;
    return this;
  }

   /**
   * Get precision
   * @return precision
  **/
  @ApiModelProperty(value = "")


  public Integer getPrecision() {

    return precision;
  }

  public void setPrecision(Integer precision) {

    this.precision = precision;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Config config = (Config) o;
    return Objects.equals(this.mode, config.mode) &&
        Objects.equals(this.clientIP, config.clientIP) &&
        Objects.equals(this.precision, config.precision);
  }

  @Override
  public int hashCode() {

    return Objects.hash(mode, clientIP, precision);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Config {\n");
    
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    clientIP: ").append(toIndentedString(clientIP)).append("\n");
    sb.append("    precision: ").append(toIndentedString(precision)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }

    return o.toString().replace("\n", "\n    ");
  }
}

