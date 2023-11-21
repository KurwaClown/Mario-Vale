package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {
    private final Game game;


    InputManager(Game game) {
        this.game = game;
    }


    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        GameState gameState = game.getGameState();


        if (gameState == GameState.PLAYING && game.getMenu().getIsEndurance()) {
            if (keyCode == KeyEvent.VK_Q) {
                game.getMario().move(false);
            } else if (keyCode == KeyEvent.VK_D) {
                game.getMario().move(true);
            } else if (keyCode == KeyEvent.VK_Z) {
                game.getMario().setVelY(-7);
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                game.pauseGame();

            } else if (keyCode == KeyEvent.VK_R) {
                game.reset();
            } else if (keyCode == KeyEvent.VK_P) {
                game.getMario().rotatePowerUp();
            }else if (keyCode == KeyEvent.VK_O) {
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
                game.reset();
            } else if (keyCode == KeyEvent.VK_P) {
                game.getMario().rotatePowerUp();
            }else if (keyCode == KeyEvent.VK_O) {
                game.toggleDebugMode();
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
                    System.exit(1);
                }
            }
        } else if (gameState == GameState.MENU) {
            if (keyCode == KeyEvent.VK_Z) {
                game.getMenu().setSelectedOption((game.getMenu().getSelectedOption() - 1 + game.getMenu().getOption().length) % game.getMenu().getOption().length);
            } else if (keyCode == KeyEvent.VK_S) {
                game.getMenu().setSelectedOption((game.getMenu().getSelectedOption() - 1 + game.getMenu().getOption().length) % game.getMenu().getOption().length);
            } else if (keyCode == KeyEvent.VK_ENTER) {
                if (game.getMenu().getSelectedOption() == 0) {
                    game.getMenu().setIsEndurance(false);
                    game.getMapManager().choosedMap();
                    game.startGame();
                } else if (game.getMenu().getSelectedOption() == 1) {
                    game.getMenu().setIsEndurance(true);
                    game.getMapManager().choosedMap();
                    game.startGame();
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
                        game.reset();
                    }
                    else if (gameState == GameState.WIN){
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
                if (keyCode == KeyEvent.VK_B) {
                    game.increaseNumClicks();
                }
            }
        }

        @Override
        public void keyTyped (KeyEvent e){

        }


    }
