package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.ProdutoDAO;
import br.com.casadocodigo.loja.model.TipoPreco;

@Controller
public class ProdutosController {
	@Autowired
	ProdutoDAO produtoDao;

	@RequestMapping("/produtos/form")
	public ModelAndView form() {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}

	@RequestMapping("/produtos")
	public String grava(Produto produto) {
		produtoDao.gravar(produto);
		return "produtos/ok";
	}
}
