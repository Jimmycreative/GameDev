package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BubbleTest {
    Bubble bubble;
    @BeforeEach
    public void initialize(){
        bubble = new Bubble(null,40,40);
    }

    /**
     * Check when the bubble width is bigger than 2500.
     */
    @Test
    void updateTest() {
        for (int i=0;i<100;i++){
            bubble.update();
        }
        assertEquals(bubble.canRemove,true);
    }


}