package daa_javaapplication1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author mainul
 */
public class EmergencyAssigning {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        //read the Vehicle locations and store them in variables
        int NumberofVehicles = 30;
        int VehicleIds[] = new int[NumberofVehicles];
        int VehicleType[] = new int[NumberofVehicles];
        int VehicleLocation[] = new int[NumberofVehicles];

        File Locations = new File("VehicleLocations.txt");
        BufferedReader LocationsBr = new BufferedReader(new FileReader(Locations));
        System.out.println("Vehicle Information:");
        for (int i = 0; i < NumberofVehicles; i++) {
            String temp[] = LocationsBr.readLine().split("\t");
            VehicleIds[i] = Integer.parseInt(temp[0]);
            VehicleType[i] = Integer.parseInt(temp[1]);
            VehicleLocation[i] = Integer.parseInt(temp[2]);

            System.out.println(temp[0] + " " + temp[1] + " " + temp[2] + "");
        }

        System.out.println("");

        //read the Vehicle Requests and store them in variables
        int NumberofRequests = 6;
        int RequestIds[] = new int[NumberofRequests];
        int RequestType[] = new int[NumberofRequests];
        int RequestLocation[] = new int[NumberofRequests];

        File Requests = new File("RequestLocations.txt");
        BufferedReader RequestsBr = new BufferedReader(new FileReader(Requests));
        System.out.println("Request Information:");
        for (int i = 0; i < NumberofRequests; i++) {
            String temp[] = RequestsBr.readLine().split("\t");
            RequestIds[i] = Integer.parseInt(temp[0]);
            RequestType[i] = Integer.parseInt(temp[1]);;
            RequestLocation[i] = Integer.parseInt(temp[2]);;
            System.out.println(temp[0] + " " + temp[1] + " " + temp[2] + "");
        }

        //build the graph (now static, later convert to dynamic)
        int Graph[][] = new int[][]{
            {0, 0, 2, 0, 0, 0, 0, 0, 4, 1},
            {0, 0, 5, 5, 0, 0, 0, 2, 0, 0},
            {2, 5, 0, 0, 2, 0, 3, 0, 0, 0},
            {0, 5, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 2, 0, 0, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 3, 0, 0},
            {0, 0, 3, 0, 2, 0, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 3, 0, 0, 2, 0},
            {4, 0, 0, 0, 0, 0, 0, 2, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        boolean VehicleAssinged[] = new boolean[NumberofVehicles];
        int AssignedId[] = new int[NumberofRequests];
        //find the single source shortest path for first request
        System.out.println("\nSolution:");
        ShortestPath SP = new ShortestPath();

        for (int k = 0; k < NumberofRequests; k++) {
            int[] distances = SP.dijkstra(Graph, RequestLocation[k] % 10);
            int MinimumDistance = Integer.MAX_VALUE;
            int TempAssign = -1;
            for (int i = 0; i < distances.length; i++) {
                if (distances[i] < MinimumDistance) {
                    for (int j = 0; j < NumberofVehicles; j++) {
                        if (((VehicleLocation[j] % 10) == i) && (VehicleAssinged[j] == false) && (RequestType[k] == VehicleType[j])) {
                            TempAssign = j;
                            MinimumDistance = distances[i];
                            break;
                        }
                    }
                }
            }
            if (TempAssign == -1) {
                System.out.println("Request can not be processed for this request, all vehicles are assigned");
            } else {
                VehicleAssinged[TempAssign] = true;
                AssignedId[k] = TempAssign + 1;
                System.out.println(RequestIds[k] + " " + RequestType[k] + " " + RequestLocation[k] + " " + AssignedId[k] + "\t" + MinimumDistance);
            }

        }

    }
}
