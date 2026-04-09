package comparator.ia.app.dtos.chat;

import comparator.ia.app.dtos.chain.price.PriceRange;

public record QuestionUser(String question, PriceRange priceSell, PriceRange priceBuy) {

}
