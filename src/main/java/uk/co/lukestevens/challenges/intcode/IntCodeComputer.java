package uk.co.lukestevens.challenges.intcode;

import java.util.function.Consumer;

public class IntCodeComputer {
	
	private IntCodeComputerMemory memory;
	private InputSource<Long> inputSource;
	private Consumer<Long> outputCallback;
		
	public IntCodeComputer(Long[] program) {
		this.memory = new IntCodeComputerMemory(program);
		this.inputSource = new InputSource<>();
		this.outputCallback = System.out::println;
	}
	
	public void setOutputCallback(Consumer<Long> outputCallback) {
		this.outputCallback = outputCallback;
	}

	public IntCodeComputerMemory getMemory() {
		return memory;
	}
	
	public void addInput(Long input) {
		this.inputSource.add(input);
	}

	public void run() {
		while(true) {
			Opcode opcode = this.parseOpcode();
			if(opcode.getAction() == OpcodeAction.WRITE) {
				this.memory.setValue(opcode.getPositionForValue(), opcode.getValue());
			}
			else if(opcode.getAction() == OpcodeAction.OUTPUT) {
				this.outputCallback.accept(opcode.getValue());
			}
			else if(opcode.getAction() == OpcodeAction.HALT) {
				return;
			}
			else if(opcode.getAction() == OpcodeAction.JUMP) {
				this.memory.jumpCursor(opcode.getPositionForValue());
			}
			this.memory.incrementCursor(opcode.getCursorIncrement());
		}
	}
	
	Opcode parseOpcode() {
		String opcode = String.valueOf(this.memory.getValue());
		OpcodeParameters params = new OpcodeParameters(opcode, this.memory);
		
		switch(params.getCommand()) {
			case 1: {
				Long p1 = params.getParameterValue(1);
				Long p2 = params.getParameterValue(2);
				return Opcode.write(p1 + p2, params.getParameterLocation(3), 4);
			}
			case 2: {
				Long p1 = params.getParameterValue(1);
				Long p2 = params.getParameterValue(2);
				return Opcode.write(p1 * p2, params.getParameterLocation(3), 4);
			}
			case 3: {
				return Opcode.write(inputSource.get(), params.getParameterLocation(1), 2);
			}
			case 4: {
				Long p1 = params.getParameterValue(1);
				return Opcode.output(p1);
			}
			case 5: {
				Long p1 = params.getParameterValue(1);
				Long p2 = params.getParameterValue(2);
				if(p1 == 0) {
					return Opcode.doNothing(3);
				}
				else {
					return Opcode.jump(p2.intValue());
				}
			}
			case 6: {
				Long p1 = params.getParameterValue(1);
				Long p2 = params.getParameterValue(2);
				if(p1 != 0) {
					return Opcode.doNothing(3);
				}
				else {
					return Opcode.jump(p2.intValue());
				}
			}
			case 7: {
				Long p1 = params.getParameterValue(1);
				Long p2 = params.getParameterValue(2);
				Long value = p1 < p2? 1L : 0L;
				return Opcode.write(value, params.getParameterLocation(3), 4);
			}
			case 8: {
				Long p1 = params.getParameterValue(1);
				Long p2 = params.getParameterValue(2);
				Long value = p1 == p2? 1L : 0L;
				return Opcode.write(value, params.getParameterLocation(3), 4);
			}
			case 9: {
				Long p1 = params.getParameterValue(1);
				this.memory.modifyRelativeBase(p1.intValue());
				return Opcode.doNothing(2);
			}
			case 99: {
				return Opcode.halt();
			}
			default: {
				throw new RuntimeException("Command " + params.getCommand() + " is not recognised");
			}
		}
	}


}
