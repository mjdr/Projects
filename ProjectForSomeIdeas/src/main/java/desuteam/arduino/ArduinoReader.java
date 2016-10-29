package desuteam.arduino;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ArduinoReader {
	public static void main(String[] args) {
		new ArduinoReader();
	}

	static SerialPort serialPort;

	public ArduinoReader() {
		serialPort = new SerialPort("COM5");
		try {
			serialPort.openPort();

			serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

			serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);

			serialPort.writeString("Hurrah!");
		} catch (SerialPortException ex) {
			System.out.println("There are an error on writing string to port т: " + ex);
		}
	}

	public void getPorts() {
		String[] portNames = SerialPortList.getPortNames();

		if (portNames.length == 0) {
			System.out.println(
					"There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
			System.out.println("Press Enter to exit...");

			return;
		}

		for (int i = 0; i < portNames.length; i++) {
			System.out.println(portNames[i]);
		}

	}

	private static class PortReader implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR() && event.getEventValue() > 0) {
				try {
					int x = serialPort.readIntArray(event.getEventValue())[0];

					System.out.println(x);
				} catch (SerialPortException ex) {
					System.out.println("Error in receiving string from COM-port: " + ex);
				}
			}
		}

	}

}
