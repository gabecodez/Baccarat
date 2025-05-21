PRJ = $(notdir $(patsubst %/,%, $(realpath .)))
PKG = baccarat
DEBUG = -g

SRC =\
	src/$(PKG)/Card.java\
	src/$(PKG)/Shoe.java\
	src/$(PKG)/Hand.java\
	src/$(PKG)/BaccaratHand.java\
	src/$(PKG)/BaccaratException.java\
	src/$(PKG)/Bettor.java\
	src/$(PKG)/Wager.java\
	src/$(PKG)/CasinoCardGame.java\
	src/$(PKG)/BaccaratCasinoGame.java\
	src/$(PKG)/BaccaratApp.java

BIN =\
	build/$(PKG)/Card.class\
	build/$(PKG)/Shoe.class\
	build/$(PKG)/Hand.class\
	build/$(PKG)/BaccaratHand.class\
	build/$(PKG)/BaccaratException.class\
	build/$(PKG)/Bettor.class\
	build/$(PKG)/Wager.class\
	build/$(PKG)/CasinoCardGame.class\
	build/$(PKG)/BaccaratCasinoGame.class\
	build/$(PKG)/BaccaratApp.class
  
IMG_SRC = src/$(PKG)/images
IMG_DST = build/images

.PHONY: all doc clean

all: $(BIN) copy-images

$(BIN): $(SRC) | build
	@javac -p ${JAVAFX_HOME}/lib/ --add-modules javafx.controls -d build src/$(PKG)/*.java $(DEBUG)
	@echo "PLEASE IGNORE THE TIME SKEW WARNING. YOUR BUILD WAS SUCCESSFUL, I PROMISE. UNIX JUST HATES ME"

build:
	@mkdir -p $@
  
copy-images:
	@mkdir -p $(IMG_DST)
	@cp -r $(IMG_SRC)/* $(IMG_DST)

clean:
	@rm -rf docs2
	@rm -rf src/$(PKG)/*.java~
	@rm -rf Makefile~

doc:
	@javadoc -p $(JAVAFX_HOME)/lib --add-modules javafx.controls -d docs2 -sourcepath src $(PKG)

run: $(CLASS_FILES) build/$(PKG)/$(STYLE)
	@java -p ${JAVAFX_HOME}/lib/ --add-modules javafx.controls -cp build $(PKG).BaccaratApp $(ARGS)