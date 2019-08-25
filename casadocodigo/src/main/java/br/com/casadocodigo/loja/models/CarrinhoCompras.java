package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();

	public void add(CarrinhoItem carrinhoItem) {
		itens.put(carrinhoItem, getQuantidade(carrinhoItem) + 1);
	}

	public Integer getQuantidade(CarrinhoItem item) {
		if (!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		return itens.get(item);
	}

	public int getQuantidade() {
		return itens.values().stream().reduce(0, (n, acc) -> n + acc);
	}

	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}

	public BigDecimal getTotal(CarrinhoItem item) {
		return item.getTotal(getQuantidade(item));
	}

	public BigDecimal getTotal() {
		return itens.keySet().//
				stream().//
				map(this::getTotal).//
				reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		produto.setId(produtoId);
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}
}
