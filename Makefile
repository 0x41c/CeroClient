BUILD_PATH = build
CMAKE = cmake

default:
		$(CMAKE) --build $(BUILD_PATH)/debug --target all

fresh: clean

clean:
	rm -rf $(BUILD_PATH)