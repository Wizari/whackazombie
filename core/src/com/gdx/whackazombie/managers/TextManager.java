package com.gdx.whackazombie.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TextManager {

    static BitmapFont font; // we draw the text to the screen using this variable
    // отображаем текст на экране через эту переменную
    // размеры области просмотра нашей игры
    static float width, height;

    public static void initialize(float width, float height) {

        font = new BitmapFont();
        TextManager.width = width;
        TextManager.height = height;
        // устанавливаем цвет шрифта красным
        font.setColor(Color.RED);
        // масштабируем размер шрифта в соответсвии с шириной экрана
        font.getData().setScale(width / 1000f);
    }

    public static void displayMessage(SpriteBatch batch) {

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "Score: " + GameManager.score);

        // отображаем результат в правом верхнем углу
        font.draw(batch, glyphLayout, width - width / 15f, height * 0.95f);

    }

}
