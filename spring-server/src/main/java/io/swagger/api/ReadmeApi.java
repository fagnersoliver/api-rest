/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-27T02:40:16.396Z")

@Api(value = "readme", description = "the readme API")
public interface ReadmeApi {

    @ApiOperation(value = "Recupera o readme da API", nickname = "readme", notes = "Esta operação retorna o readme da API", tags={ "API", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Sucesso ao recuperar o reame da API."),
        @ApiResponse(code = 500, message = "Erro interno ao tentar realizar a consulta.") })
    @RequestMapping(value = "/readme",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<String> readme();

}
