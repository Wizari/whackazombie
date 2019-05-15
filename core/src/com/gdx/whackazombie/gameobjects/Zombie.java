package com.gdx.whackazombie.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.whackazombie.managers.GameManager;


public class Zombie {

    public Sprite zombieSprite; // спрайт для отображения зомби
    public Vector2 position = new Vector2();// позиция зомби
    public float height, width; // размеры зомби
    public float scaleFactor; // коэффициент масштабирования зомби

    public enum State {GOINGUP, GOINGDOWN, UNDERGROUND, STUNNED}

    ; // определение состояний зомби
    public State state = State.GOINGUP; // переменная, описывающая текущее состояние зомби
    public float currentHeight = 0.0f; // текущая величина высоты зомби относительно ямы
    public float speed = 2f; // скорость, с которой зомби будет двигаться вверх/вниз
    public float timeUnderGround = 0.0f; // время с тех пор, как зомби спрятался под землю
    public float maxTimeUnderGround = 0.8f; // максимальное время, разрешенное зомби оставаться под землей
    public float stunTime = 0.1f; // общее время оглушения, в котором будет находиться зомби
    public float stunCounter = 0.0f; // время в которое зомби был оглушен
    public Sprite stunSprite; // sprite для отображения изображения оглушения


    public void render(SpriteBatch batch) {

        zombieSprite.draw(batch);
        if(state==State.STUNNED){
            stunSprite.draw(batch);
        }
    }

    public void update() {
        switch (state) {
            case UNDERGROUND:
                if (timeUnderGround >= maxTimeUnderGround) {
                    state = State.GOINGUP;
                    timeUnderGround = 0.0f;
                } else {
                    timeUnderGround += Gdx.graphics.getDeltaTime();
                    System.out.println();
                }
                break;
            // увеличиваем высоту до максимума, как только высота достигнет максимума - меняем состояние
            case GOINGUP:
                currentHeight += speed;
                if (currentHeight >= height) {
                    currentHeight = height;
                    state = State.GOINGDOWN;
                }
                break;
            // уменьшаем высоту до минимума(0), как только выоста достигнет минимума - меняем состояние
            case GOINGDOWN:
                currentHeight -= speed;
                if (currentHeight <= 0.0) {
                    currentHeight = 0.0f;
                    state = State.UNDERGROUND;
                }
                break;
            case STUNNED:
                if(stunCounter>=stunTime){
                    //отправляем зомби под землю
                    state= State.UNDERGROUND;
                    stunCounter=0.0f;
                    currentHeight=0.0f;
                    randomizeWaitTime();
                }
                else{
                    stunCounter+=Gdx.graphics.getDeltaTime();
                }
                break;
        }
        // рисуем только часть изображения зомби. Зависит от высоты над ямой
        zombieSprite.setRegion(0, 0, (int) (width / scaleFactor), (int) (currentHeight / scaleFactor));
        zombieSprite.setSize(zombieSprite.getWidth(), currentHeight);
    }

    public void randomizeWaitTime() {
        maxTimeUnderGround = (float) Math.random() * 2f;
    }

    public boolean handleTouch(float touchX, float touchY) {
        if ((touchX >= position.x) && touchX <= (position.x + width) && (touchY >= position.y)
                && touchY <= (position.y + currentHeight)) {

            float volume = 0.5f;//настраивать громкость звука
//            GameManager.hitSound.play();
            GameManager.hitSound.play(volume); //проигрывать звук при клике игром по зомби:
            stunSprite.setPosition(position.x+width-(stunSprite.getWidth()/2),
                    position.y+currentHeight -(stunSprite.getHeight()/2));

            state = State.STUNNED; // меняем состояние на UNDERGROUND
            return true;
        }
        return false;
    }

}
