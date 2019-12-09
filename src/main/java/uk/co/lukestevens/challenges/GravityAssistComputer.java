package uk.co.lukestevens.challenges;

import uk.co.lukestevens.challenges.intcode.IntCodeComputer;
import uk.co.lukestevens.challenges.intcode.IntCodeComputerFactory;
import uk.co.lukestevens.challenges.intcode.IntCodeComputerMemory;

public class GravityAssistComputer {
	
	private final IntCodeComputerFactory factory;
			
	public GravityAssistComputer(int[] input) {
		this.factory = new IntCodeComputerFactory(input);
	}
	
	public Integer[] run() {
		IntCodeComputer computer = factory.createComputer();
		computer.run();
		return computer.getMemory().getBuffer();
	}
	
	public int run(int noun, int verb) {
		IntCodeComputer computer = factory.createComputer();
		IntCodeComputerMemory memory = computer.getMemory();
		memory.setValue(1, noun);
		memory.setValue(2, verb);
		computer.run();
		return computer.getMemory().getValue(0);
	}
	
	public int findInputsForValue(int output) {
		for(int noun = 0;noun < 100; noun++) {
			for(int verb = 0; verb < 100; verb++) {
				int actualOutput = this.run(noun, verb);
				if(actualOutput == output) {
					return (100 * noun) + verb;
				}
			}
		}
		return -1;
	}

}
