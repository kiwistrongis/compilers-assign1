# globals
default: build reports
freshen: clean default
clean:
	rm -rf bin/* pkg/*

# vars
warnings =
cp = bin
dest = -d bin

class_files = \
	bin/TestAcceptance.class
pdf_files = \
	bin/report.pdf
pkg_file = pkg/A1_100425828.zip

# compilation definitions
$(class_files): bin/%.class : src/%.java
	javac -cp $(cp) $(dest) $(warnings) $<

$(pdf_files): bin/%.pdf : src/%.md src/metadata.yaml makefile
	pandoc \
		-r markdown+link_attributes \
		src/metadata.yaml $< \
		-o $@
#		--template=src/template.tex \

$(pkg_file): $(pdf_files)
	cp \
		src/TestAcceptance.java \
		data/accepted.txt \
		data/rejected.txt \
		bin
	cp data/dfa.txt bin/MyDFA
	cd bin && \
	zip -FSr ../$(pkg_file) \
		report.pdf \
		TestAcceptance.java \
		MyDFA \
		accepted.txt \
		rejected.txt \

# commands
all: build reports package
build: $(class_files)
report: $(pdf_files)
reports: $(pdf_files)
package: $(pkg_file)

ci:
	make-ci bin/report.pdf src/report.md src/metadata.yaml

run: test-accept
test: test-custom

# tests
test-report: bin/report.pdf
	evince $<

test-custom: bin/TestAcceptance.class
	java -cp $(cp) TestAcceptance data/dfa.txt data/test.txt

test-accept: bin/TestAcceptance.class
	java -cp $(cp) TestAcceptance data/dfa.txt data/accepted.txt
test-reject: bin/TestAcceptance.class
	java -cp $(cp) TestAcceptance data/dfa.txt data/rejected.txt
