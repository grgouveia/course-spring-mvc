package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	CarrinhoCompras carrinhoCompras;
	
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public ModelAndView finalizar(RedirectAttributes attr) {
		System.out.println(carrinhoCompras.getTotal());
		attr.addFlashAttribute("sucesso", "Pagamento realizado com Sucesso!");
		ModelAndView modelAndView = new ModelAndView("redirect:/produtos");
		return modelAndView;
	}
}
