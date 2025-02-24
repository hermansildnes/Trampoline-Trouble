package game.environment;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import game.player.Player;

public class TrampolineSensorListener implements SensorListener {
    private TrampolineAnimationTrigger trampolineAnimationTrigger;

    public TrampolineSensorListener(TrampolineAnimationTrigger trampolineAnimationTrigger) {
        this.trampolineAnimationTrigger = trampolineAnimationTrigger;
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player) {
            ((Player)e.getContactBody()).startAnimation(Player.AnimationState.JUMP);
        }
    }

    @Override
    public void endContact(SensorEvent e) {
    }
    
}
