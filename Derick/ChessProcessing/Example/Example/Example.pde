final int MAIN = 0;
final int GAME = 1;

int windowWidth = 1080;
int windowHeight = 720;

int buttonWidth = 300;
int buttonHeight = 80;

int startButtonX = windowWidth/2 - buttonWidth/2;
int startButtonY = buttonHeight * 2;

color startButtonColor = color(255);
color startOverButtonColor = color(220);

boolean startOver = false;


int gameState = MAIN;

void settings() {
  size(windowWidth, windowHeight);
}

void setup() {
  background(255);
}

void draw() {
  switch (gameState) {
    case MAIN: 
      update();
      
      if (startOver) {
        fill(startOverButtonColor);
      } else {
        fill(startButtonColor);
      }

      rect(startButtonX, startButtonY, buttonWidth, buttonHeight);
      
      textSize(24);
      fill(0, 0, 0);
      textAlign(CENTER, CENTER);
      text("Start the game", startButtonX + buttonWidth / 2, startButtonY + buttonHeight / 2 - 5);
    break;
    case GAME:
      background(255);
      // showing board
      break;
    default :
        
      break;	
  }
}

boolean overButton(int x, int y, int width, int height) {
  if (mouseX >= x && mouseX <= x+width &&
    mouseY >= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
}

void update() {
  if ( overButton(startButtonX, startButtonY, buttonWidth, buttonHeight) ) {
    startOver = true;
  } else {
    startOver = false;
  }
}

void mouseReleased() {
  switch (gameState) {
    case MAIN:
      if (mouseX >= startButtonX && mouseX <= startButtonX+buttonWidth# &&
      mouseY >= startButtonY && mouseY <= startButtonY+buttonHeight) {
        gameState = GAME;
      }
    break;
    case GAME:
      break;
    default:
    break;
  }
}
