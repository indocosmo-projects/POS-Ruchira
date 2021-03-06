/** 
 * The main class which starts the application.
 */
package com.indocosmo.pos.test;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.ParallelPort;
import gnu.io.PortInUseException;

import java.io.IOException;
import java.io.OutputStream;


public class ParallelCommunication {

    private static OutputStream outputStream;;
    private static ParallelPort parallelPort;
    private static CommPortIdentifier port;

    // CONSTANTS
    public static final String PARALLEL_PORT = "/dev/ttyUSB0";

    public static final String[] PORT_TYPE = { "Serial Port", "Parallel Port" };

    // these commands are specific for my printer around the text
    private static String printerCodes = "--------------------------HelloWorld!---------------------------";

    public static void main(String[] args) {

        System.out.println("Started test....");

        try {
            // get the parallel port connected to the printer
            port = CommPortIdentifier.getPortIdentifier(PARALLEL_PORT);

            System.out.println("\nport.portType = " + port.getPortType());
            System.out.println("port type = "
                    + PORT_TYPE[port.getPortType() - 1]);
            System.out.println("port.name = " + port.getName());

            // open the parallel port -- open(App name, timeout)
            parallelPort = (ParallelPort) port.open("CommTest", 50);
            outputStream = parallelPort.getOutputStream();

            char[] charArray = printerCodes.toCharArray();
            byte[] byteArray =printerCodes.getBytes();

            System.out.println("Write...");
            outputStream.write(byteArray);
            System.out.println("Flush...");
            outputStream.flush();
            System.out.println("Close...");
            outputStream.close();

        } catch (NoSuchPortException nspe) {
            System.out.println("\nPrinter Port "+PARALLEL_PORT+" not found : "
                    + "NoSuchPortException.\nException:\n" + nspe + "\n");
        } catch (PortInUseException piue) {
            System.out.println("\nPrinter Port "+PARALLEL_PORT+" is in use : "
                    + "PortInUseException.\nException:\n" + piue + "\n");
        }
        /*
         * catch (UnsupportedCommOperationException usce) {
         * System.out.println("\nPrinter Port LPT1 fail to write :
         * UnsupportedCommException.\nException:\n" + usce + "\n"); }
         */
        catch (IOException ioe) {
            System.out.println("\nPrinter Port LPT1 failed to write : "
                    + "IOException.\nException:\n" + ioe + "\n");
        } catch (Exception e) {
            System.out
                    .println("\nFailed to open Printer Port LPT1 with exeception : "
                            + e + "\n");
        } finally {
            if (port != null && port.isCurrentlyOwned()) {
                parallelPort.close();
            }

            System.out.println("Closed all resources.\n");
        }
    }
}