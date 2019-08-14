package io.swagger.api;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import io.swagger.api.dao.ClienteDAO;
import io.swagger.model.Cliente;
import io.swagger.model.Clientes;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-27T02:40:16.396Z")

@Controller
public class ClienteApiController implements ClienteApi {

	private static final Logger log = LoggerFactory.getLogger(ClienteApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	private ClienteDAO clienteDAO;

	@org.springframework.beans.factory.annotation.Autowired
	public ClienteApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
		this.clienteDAO = new ClienteDAO();
	}

	public ResponseEntity<Cliente> alteraExistente(
			@ApiParam(value = "ID do cliente", required = true) @PathVariable("id") Integer id,
			@ApiParam(value = "", required = true) @Valid @RequestBody Cliente cliente) {

		ResponseEntity<Cliente> responseEntity = null;

		try {
			cliente.setId(id);
			Cliente clienteUpdate = clienteDAO.altera(cliente);

			if (clienteUpdate == null) {
				throw new RuntimeException("Erro ao tentar cadastrar um novo cliente");
			}

			responseEntity = new ResponseEntity<Cliente>(clienteUpdate, getHeaderLocation(clienteUpdate.getId()),
					HttpStatus.ACCEPTED);

		} catch (Exception e) {
			log.error("Falha ao tentar cadastrar o cliente.", e);
			responseEntity = new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return responseEntity;

	}

	private MultiValueMap<String, String> getHeaderLocation(Integer id) {
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("location", location.getPath());
		return headers;
	}

	public ResponseEntity<Cliente> alteraStatusPorId(
			@ApiParam(value = "Status do cliente.", required = true, allowableValues = "\"ativo\", \"inativo\"") @PathVariable("status") String status,
			@ApiParam(value = "Numero do id do cliente.", required = true) @PathVariable("id") Integer id) {

		ResponseEntity<Cliente> responseEntity = null;

		try {

			Cliente clienteUpdateStatus = clienteDAO.alteraStatusPorId(id, status);

			if (clienteUpdateStatus == null) {
				throw new RuntimeException("Erro ao tentar atualizar o cliente");
			}

			responseEntity = new ResponseEntity<Cliente>(clienteUpdateStatus,
					getHeaderLocation(clienteUpdateStatus.getId()), HttpStatus.ACCEPTED);

		} catch (Exception e) {
			log.error("Falha ao tentar alterar o status do cliente.", e);
			responseEntity = new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return responseEntity;

	}

	public ResponseEntity<Cliente> cadastraNovo(
			@ApiParam(value = "", required = true) @Valid @RequestBody Cliente cliente) {

		ResponseEntity<Cliente> responseEntity = null;

		try {

			if (ehValido(cliente)) {

				Cliente clienteNew = clienteDAO.salva(cliente);

				if (clienteNew == null) {
					throw new RuntimeException("Erro ao tentar cadastrar um novo cliente");
				}

				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(clienteNew.getId()).toUri();
				MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
				headers.add("location", location.getPath());

				responseEntity = new ResponseEntity<Cliente>(clienteNew, headers, HttpStatus.CREATED);

			} else {

				responseEntity = new ResponseEntity<Cliente>(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Falha ao tentar cadastrar o cliente.", e);
			responseEntity = new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return responseEntity;
	}

	private boolean ehValido(Cliente cliente) {
		if (cliente != null) {
			return true;
		}
		return false;
	}

	public ResponseEntity<Cliente> consultaPorId(
			@ApiParam(value = "Numero do id do cliente.", required = true) @PathVariable("id") Integer id) {

		ResponseEntity<Cliente> responseEntity = null;

		try {

			Cliente cliente = clienteDAO.consultaPorId(id);

			if (cliente == null) {
				responseEntity = new ResponseEntity<Cliente>(cliente, HttpStatus.NOT_FOUND);
			} else {
				responseEntity = new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Falha ao tentar cadastrar o cliente.", e);
			new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	public ResponseEntity<Clientes> consultaPorSobrenome(
			@ApiParam(value = "Sobrenome do cliente.", required = true) @PathVariable("sobrenome") String sobrenome) {

		ResponseEntity<Clientes> responseEntity = null;

		try {

			List<Cliente> clientelz = clienteDAO.consultaPorSobrenome(sobrenome);

			if ((clientelz == null) || (clientelz.size() <= 0)) {
				responseEntity = new ResponseEntity<Clientes>(HttpStatus.NOT_FOUND);

			} else {

				responseEntity = new ResponseEntity<Clientes>(
						objectMapper.readValue(objectMapper.writeValueAsBytes(clientelz), Clientes.class),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Falha ao tentar procurar o cliente.", e);
			responseEntity = new ResponseEntity<Clientes>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}

	public ResponseEntity<Void> excluiExistente(
			@ApiParam(value = "Numero do id do cliente.", required = true) @PathVariable("id") Integer id) {

		ResponseEntity<Void> responseEntity = null;

		try {

			boolean excluido = clienteDAO.exclui(id);

			if (excluido) {
				responseEntity = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

			} else {

				throw new RuntimeException("Erro ao tentar excluir cliente");
			}

		} catch (Exception e) {
			log.error("Falha ao tentar excluir o cliente.", e);
			responseEntity = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}

}
