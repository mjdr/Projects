package vm;

import static vm.Bytecode.*;

public class VM 
{
	int[] code,stack,global;
	int ip;
	int sp;
	int fp;
	
	
	public VM(int[] code , int startPoint) 
	{
		this.code = code;
		ip = startPoint;
		sp = -1;
		fp = 0;

		stack = new int[100];
		global = new int[100];
		
	}
	

	public void stackPush(int data){ sp++;stack[sp] = data; }
	public int stackPop(){ int v = stack[sp]; sp--; return v; }
	
	public void cpu()
	{
		printStack();
		while(ip < code.length)
		{
			int command = code[ip];
			ip++;
			switch (command) 
			{
			
				case ICONST:
					stackPush(code[ip]);
					ip++;
				break;
				
				case PRINT:
					System.out.println("PRINT: "+stackPop());
				break;
				
				case GSTORE:
					stackPush(global[code[ip]]);
					ip++;
				break;
				
				case GLOAD:
					global[code[ip]] = stackPop();
					ip++;
				break;
				
				case JUMP:
					ip = code[ip];
				break;
				
				case IADD:
					int b = stackPop();
					int a = stackPop();
					stackPush(a + b);
				break;

				case ISUB:
					b = stackPop();
					a = stackPop();
					stackPush(a - b);
				break;

				case IMUL:
					b = stackPop();
					a = stackPop();
					stackPush(a * b);
				break;
				
				case CALL:
					//CALL pointer , numOfArgs
					stackPush(code[ip + 1]);//numOfarg
					stackPush(ip + 2);//ret addr
					stackPush(fp);
					stackPush(sp - 3 - code[ip + 1]);
					fp = sp;
					ip = code[ip];
					
				break;
				case RET:
					int v = stackPop();

					ip = stack[fp-2];
					sp = stack[fp];
					fp = stack[fp-1];
					stackPush(v);
				break;
				case LOAD:
					stackPush(stack[fp+code[ip]]);
					ip++;
				break;
				case POP:
					sp--;
				break;
			}
			printStack();
			
			
		}
		
	}


	private void printStack() 
	{
		System.out.print("[");
		for(int i = 0;i <= sp;i++)
		{
			System.out.print(" "+stack[i]);
		}
		System.out.println("]");
	}
	
	
	
}
