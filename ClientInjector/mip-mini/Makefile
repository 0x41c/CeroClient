.SECONDARY:
.PHONY: all

CFLAGS := -I. -Werror -O3 -Wno-deprecated-declarations
SYSROOT ?= $(shell xcodebuild -sdk macosx -version Path 2> /dev/null)
CFLAGS += -isysroot $(SYSROOT)

ifeq ($(shell uname -m),arm64)
ARCH1 := arm64e
ARCH2 := x86_64
else
ARCH1 := x86_64
endif

SIGN_IDENTITY ?= CodeSign
CODESIGN_TARGET := codesign -s "$(SIGN_IDENTITY)"

all: build/inject.a finish

build/inject.a: build/inject.o
	ar -rcs $@ $^

build/inject.o: build/injector/injector.o
	mkdir -p $(dir $@)
	ld -r $^ -o $@

build/injector/injector.o: build/injector/inject/inject.c.o build/injector/payloads/injected_$(ARCH1).c.bin
	ld -r $(filter %.o,$^) -o $@ -sectcreate __INJ_$(ARCH1) __inj_$(ARCH1) build/injector/payloads/injected_$(ARCH1).c.bin -arch $(ARCH1)
	
build/%.o: %
	mkdir -p $(dir $@)
	$(CC) $(CFLAGS) -arch $(ARCH1) -c $^ -o $@
	
build/injector/payloads/injected_x86_64.c.dylib: injector/payloads/injected_x86_64.c
	mkdir -p $(dir $@)
	$(CC) $(CFLAGS) -shared -arch x86_64 -Oz -Wl,-order_file,injector/payloads/order $^ -o $@

build/injector/payloads/injected_arm64e.c.dylib: injector/payloads/injected_arm64e.c
	mkdir -p $(dir $@)
	$(CC) $(CFLAGS) -shared -arch arm64e -Oz -Wl,-order_file,injector/payloads/order $^ -o $@

build/injector/payloads/%.bin: build/injector/payloads/%.dylib
	gobjcopy -Obinary $^ $@

finish:
	cp -f build/inject.a ../resources

clean:
	rm -rf build
