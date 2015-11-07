package vm;

import static vm.Bytecode.*;

public class Test 
{
	public static void main(String[] args) 
	{
		int[] code = new int[]{
				ICONST,2, //0
				LOAD,-4, //0
				CALL,12,1, //2
				IADD,     //3
				ICONST,5, //5
				IMUL,     //6
				RET,

				ICONST,3,
				ICONST,2,
				IMUL,
				LOAD,-4,
				IADD,
				RET,
				
				ICONST,5,
				CALL ,0,1,//7
				PRINT,    //10
				EXIT
				
		};
		VM vm = new VM(code, 21);
		vm.cpu();
	}
}
