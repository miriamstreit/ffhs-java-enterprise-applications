MAINDOC = main
LTXARGS = -pdf -lualatex -use-make -shell-escape
FILES   = $(shell echo chapters/*.tex)

.PHONY: $(MAINDOC).pdf all clean

all: $(MAINDOC).pdf

$(MAINDOC).pdf: $(MAINDOC).tex $(FILES)
	latexmk $(LTXARGS) $(MAINDOC).tex

clean:
	latexmk -CA
	rm -rf *.aux *.bbl *.ist *.lol *.log *.fdb* *.fls *.pyg src/*.aux _minted-$(MAINDOC) $(MAINDOC)*.bib $(MAINDOC)*.xml
