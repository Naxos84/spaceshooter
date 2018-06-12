package io.github.naxos84.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import io.github.naxos84.spaceshooter.screen.MainMenuScreen;

import java.util.Locale;

public class SpaceShooter extends Game {

    public static final float SCREEN_WIDTH = 800;
    public static final float HEIGHT = 600;

    public SpriteBatch batch;
    public BitmapFont font;
    private final boolean debugMode;
    private Locale locale;
    private FileHandle baseFileHandle;
    public I18NBundle bundle;
    private GamePreferences gamePreferences;

    private Music menuMusic;
    private Music gameMusic;

    public SpaceShooter(final boolean debugMode) {
        this.debugMode = debugMode;
        locale = Locale.getDefault();
        gamePreferences = new GamePreferences();
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
        bundle = I18NBundle.createBundle(baseFileHandle, locale);
    }

    public GamePreferences getGamePreferences() {
        return gamePreferences;
    }

    @Override
    public void create() {

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/menu_screen_loop.ogg"));
        menuMusic.setLooping(true);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/game_background_loop.ogg"));
        gameMusic.setLooping(true);

        batch = new SpriteBatch();
        font = new BitmapFont();
        baseFileHandle = Gdx.files.internal("i18n/lang");
        bundle = I18NBundle.createBundle(baseFileHandle, locale);
        this.setScreen(new MainMenuScreen(this, debugMode));

    }

    public void playMenuMusic() {
        if (getGamePreferences().isMusicEnabled()) {
            if (!menuMusic.isPlaying()) {
                gameMusic.stop();
                menuMusic.setVolume(getGamePreferences().getMusicVolume());
                menuMusic.play();
            }
        } else {
            gameMusic.stop();
            menuMusic.stop();
        }
    }

    public void playGameMusic() {
        if (getGamePreferences().isMusicEnabled()) {
            if (!gameMusic.isPlaying()) {
                menuMusic.stop();
                gameMusic.setVolume(getGamePreferences().getMusicVolume());
                gameMusic.play();
            }
        } else {
            gameMusic.stop();
            menuMusic.stop();
        }
    }

    @Override
    public void render() {
        super.render();

    }


    @Override
    public void dispose() {
        menuMusic.stop();
        menuMusic.dispose();
        gameMusic.stop();
        gameMusic.dispose();
        this.screen.dispose();

    }

}