package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.ProdutoDAO;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validations.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	@Autowired
	ProdutoDAO produtoDao;

	@Autowired
	FileSaver fileSaver;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("form")
	public ModelAndView form(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView grava(MultipartFile sumario, @Valid Produto produto, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return form(produto);
		}

		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);

		produtoDao.gravar(produto);
		attributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		return new ModelAndView("redirect:produtos");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		List<Produto> produtos = produtoDao.listar();
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}

	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		Produto produto = produtoDao.find(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
}
