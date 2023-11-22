package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class InputManager implements KeyListener {
    private final Game game;


    InputManager(Game game) {
        this.game = game;
    }


    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        GameState gameState = game.getGameState();


        if (gameState == GameState.PLAYING && game.getMenu().isEndurance()) {
            if (keyCode == KeyEvent.VK_Q) {
                game.getMario().move(false);
            } else if (keyCode == KeyEvent.VK_D) {
                game.getMario().move(true);
            } else if (keyCode == KeyEvent.VK_Z) {
                game.getMario().setVelY(-7);
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                game.pauseGame();
            } else if (keyCode == KeyEvent.VK_R) {
                game.resetMap();
            } else if (keyCode == KeyEvent.VK_F2) {
                game.getMario().rotatePowerUp();
            }else if (keyCode == KeyEvent.VK_F1) {
                game.toggleDebugMode();
            }
        }
        else if(gameState == GameState.PLAYING) {
            if (keyCode == KeyEvent.VK_SPACE) {
                game.getMario().jump();
            } else if (keyCode == KeyEvent.VK_Q) {
                game.getMario().move(false);
            } else if (keyCode == KeyEvent.VK_D) {
                game.getMario().move(true);
            } else if (keyCode == KeyEvent.VK_E) {
                game.getMario().attack(game.getMap());
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                game.pauseGame();
            } else if (keyCode == KeyEvent.VK_R) {
                game.resetMap();
            } else if (keyCode == KeyEvent.VK_F1) {
                game.getMario().rotatePowerUp();
            }else if (keyCode == KeyEvent.VK_F2) {
                game.toggleDebugMode();
            } else if(keyCode == KeyEvent.VK_F3){
                game.teleportToFlag();
            }
        }else if (gameState == GameState.PAUSED) {
            if (keyCode == KeyEvent.VK_Z) {
                game.getMenu().setSelectedOption((game.getMenu().getSelectedOption() - 1 + game.getMenu().getOption().length) % game.getMenu().getOption().length);
            } else if (keyCode == KeyEvent.VK_S) {
                game.getMenu().setSelectedOption((game.getMenu().getSelectedOption() - 1 + game.getMenu().getOption().length) % game.getMenu().getOption().length);
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                game.resumeGame();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                if (game.getMenu().getSelectedOption() == 0) {
                    game.resumeGame();
                } else if (game.getMenu().getSelectedOption() == 1) {
                    game.getMap().reset();
                    game.getMario().reset();
                    game.goToMainMenu();

                }
            }
        } else if(gameState == GameState.TRANSFORMATION){
            if (keyCode == KeyEvent.VK_SPACE) {
                if (!game.getShoot().isAngleLocked()) {
                    game.getShoot().lockAngle();
                } else if (!game.getShoot().isPowerLocked()) {
                    game.getShoot().lockPower();
                }
            }
        }else if (gameState == GameState.MENU) {
            if (keyCode == KeyEvent.VK_Z) {
                game.getMenu().setSelectedOption((game.getMenu().getSelectedOption() - 1 + game.getMenu().getOption().length) % game.getMenu().getOption().length);
            } else if (keyCode == KeyEvent.VK_S) {
                System.out.println(Arrays.toString(game.getMenu().getOption()));
                game.getMenu().setSelectedOption((game.getMenu().getSelectedOption() + 1) % game.getMenu().getOption().length);
            } else if (keyCode == KeyEvent.VK_ENTER) {
                if (game.getMenu().getSelectedOption() == 0) {
                    game.getMenu().setEndurance(false);
                    game.getMapManager().choosedMap();
                    game.startGame();
                    game.resetPreviousCameraX();
                    game.resetMap();
                } else if (game.getMenu().getSelectedOption() == 1) {
                    game.getMenu().setEndurance(true);
                    game.getMario().setPigglet(false);
                    game.getMario().setBigPig(false);
                    game.resetPreviousCameraX();
                    game.getMapManager().resetLastGeneratedX();
                    game.getMapManager().choosedMap();
                    game.startGame();
                    game.resetPreviousCameraX();
                    game.resetMap();
                } else if (game.getMenu().getSelectedOption() == 2){
                    System.exit(1);
                }
            }
            else if (keyCode == KeyEvent.VK_ESCAPE) {
                game.quitGame();
            }
        } else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) {

            if (keyCode == KeyEvent.VK_Z) {
                game.getMenu().setSelectedOption((game.getMenu().getSelectedOption() - 1 + game.getMenu().getOption().length) % game.getMenu().getOption().length);
            } else if (keyCode == KeyEvent.VK_S) {
                game.getMenu().setSelectedOption((game.getMenu().getSelectedOption() - 1 + game.getMenu().getOption().length) % game.getMenu().getOption().length);
            } else if (keyCode == KeyEvent.VK_ENTER) {
                if (game.getMenu().getSelectedOption() == 0) {
                    if (gameState == GameState.GAMEOVER) {
                        game.resetPreviousCameraX();
                        game.resetMap();
                        game.getMario().setPigglet(false);
                        game.getMario().setBigPig(false);
                    }
                    else if (gameState == GameState.WIN){
                        game.resetPreviousCameraX();
                        game.resetMap();
                        game.getMario().setReadyToKick(false);
                        game.getMario().setDontMove(false);
                        game.getShoot().setAngleLocked(false);
                        game.getShoot().setPowerLocked(false);
                        game.getShoot().setTransformed(false);
                        game.setNumClicks(0);
                        game.nextLevel();
                    }
                } else if (game.getMenu().getSelectedOption() == 1) {
                    System.exit(1);
                }
            }

        }
    }
        @Override
        public void keyReleased (KeyEvent event){
            int keyCode = event.getKeyCode();
            GameState gameState = game.getGameState();
            if (gameState == GameState.PLAYING) {
                if (keyCode == KeyEvent.VK_Q) {
                    game.getMario().stop(false);
                } else if (keyCode == KeyEvent.VK_D) {
                    game.getMario().stop(true);
                }
            }
            else if(gameState == GameState.FLAG){
                if (keyCode == KeyEvent.VK_SPACE) {
                    game.increaseNumClicks();
                }
            }
        }

        @Override
        public void keyTyped (KeyEvent e){

        }


    }
