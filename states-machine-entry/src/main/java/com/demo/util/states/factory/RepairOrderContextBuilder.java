package com.demo.util.states.factory;



import com.demo.util.states.action.IOrderAction;
import com.demo.util.states.action.ProcessStartAction;
import com.demo.util.states.action.SignOrderAction;
import com.demo.util.states.enums.OrderEvent;
import com.demo.util.states.enums.OrderState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laihz
 * @date 2023/11/30 15:34
 */
@Getter
@Setter
@Component
public class RepairOrderContextBuilder implements InitializingBean {

    OrderState initState = OrderState.WAITING_SIGNED;

    List<OrderTransition<?>> transitionConfigurers = new ArrayList<>();


    Map<OrderEvent, OrderTransition<?>> triggerToTransitionMap = new HashMap<>();

    public RepairOrderContextBuilder() {

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        initTransition();
        for (OrderTransition<?> transition : transitionConfigurers) {
            triggerToTransitionMap.put(transition.getEvent(), transition);
        }
    }


    public <P> OrderTransition<P> with() {
        OrderTransition<P> orderTransition = new OrderTransition<>();
        transitionConfigurers.add(orderTransition);
        orderTransition.setBuilder(this);
        return orderTransition;
    }

    public <P> OrderTransition<P> with(IOrderAction<P> action) {
        OrderTransition<P> orderTransition = new OrderTransition<>();
        orderTransition.setAction(action);
        transitionConfigurers.add(orderTransition);
        orderTransition.setBuilder(this);
        return orderTransition;
    }


    /**
     * 一个事件对应一个source和target
     *
     * @param
     * @return void
     * @author laihz
     * @date 2023/12/1 15:49
     */
    private void initTransition() {
        this.with(new SignOrderAction()).source(OrderState.WAITING_SIGNED).target(OrderState.WAITING_PROCESS).event(OrderEvent.SIGN)
                .and()
                .with().source(OrderState.WAITING_SIGNED).target(OrderState.REFUSE_SIGN_WAITING_REVIEW).event(OrderEvent.REFUSE_SIGN)
                .and()
                .with().source(OrderState.REFUSE_SIGN_WAITING_REVIEW).target(OrderState.WAITING_SIGNED).event(OrderEvent.REFUSE_SIGN_REVIEW_REJECT)
                .and()
                .with().source(OrderState.REFUSE_SIGN_WAITING_REVIEW).target(OrderState.PROCESSED).event(OrderEvent.REFUSE_SIGN_REVIEW_PASS)
                .and()
                .with(new ProcessStartAction()).source(OrderState.WAITING_PROCESS).target(OrderState.PROCESSING).event(OrderEvent.PROCESS_START)
                .and()
                .with().source(OrderState.PROCESSING).target(OrderState.SUSPEND_WAITING_REVIEW).event(OrderEvent.SUSPEND)
                .and()
                .with().source(OrderState.PROCESSING).target(OrderState.PROCESSED_WAITING_REVIEW).event(OrderEvent.PROCESS_FINISH)
                .and()
                .with().source(OrderState.SUSPEND_WAITING_REVIEW).target(OrderState.PROCESSING).event(OrderEvent.SUSPEND_REVIEW_PASS)
                .and()
                .with().source(OrderState.SUSPEND_WAITING_REVIEW).target(OrderState.SUSPENDED).event(OrderEvent.SUSPEND_REVIEW_REJECT)
                .and()
                .with().source(OrderState.SUSPENDED).target(OrderState.PROCESSING).event(OrderEvent.SUSPEND_MANUAL_UNDO)
                .and()
                .with().source(OrderState.SUSPENDED).target(OrderState.PROCESSING).event(OrderEvent.SUSPEND_AUTO_UNDO)
                .and()
                .with().source(OrderState.PROCESSED_WAITING_REVIEW).target(OrderState.PROCESSING).event(OrderEvent.PROCESS_REVIEW_REJECT)
                .and()
                .with().source(OrderState.PROCESSED_WAITING_REVIEW).target(OrderState.PROCESSED).event(OrderEvent.PROCESS_REVIEW_PASS)
                .and()
                .with().source(OrderState.PROCESSED_WAITING_REVIEW).target(OrderState.PROCESSED).event(OrderEvent.PROCESS_REVIEW_AUTO_PASS);
    }


}
