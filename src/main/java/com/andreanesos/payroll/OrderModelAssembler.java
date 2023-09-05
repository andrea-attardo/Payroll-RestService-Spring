package com.andreanesos.payroll;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>>{
    
    //prende un order e lo converte in entitymodel(che sarebbe un 
    //rapsentational model che quindi ha risorse come link
    @Override
    public EntityModel<Order> toModel(Order order){
        EntityModel<Order> orderModel = EntityModel.of(order, 
                linkTo(methodOn(OrderController.class).findOne(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).findAll()).withRel("orders"));
        
        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
          }
        
        
        return orderModel;
    }
    
    
}
