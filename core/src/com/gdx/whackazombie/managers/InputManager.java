package com.gdx.whackazombie.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.gdx.whackazombie.gameobjects.Zombie;

public class InputManager {

    static Vector3 temp = new Vector3();

    public static void handleInput(OrthographicCamera camera) {

        // Проверяем было ли касание по экрану
        if (Gdx.input.justTouched()) {
            // Получаем входные координаты касания и
            // устанавливаем эти координаты в временный вектор
            temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            // получаем координаты касания относительно области просмотра нашей камеры
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY = temp.y;
            // выполняем итерацию массива наших зомби и проверяем были ли выполнено касание по зомби?
            for (int i = 0; i < GameManager.zombies.size; i++) {
                Zombie zombie = GameManager.zombies.get(i);
                if (zombie.state!= Zombie.State.STUNNED && zombie.handleTouch(touchX, touchY)) {
                    GameManager.score++; // увеличиваем на единицу значение переменной score
                    break;
                }
            }
        }
    }
}