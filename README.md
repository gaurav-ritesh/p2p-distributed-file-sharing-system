# Peer to Peer Distributed file sharing system

## Abstract 

This project presents a Peer-to-Peer (P2P) Distributed File Sharing System, revolutionizing traditional file sharing by eliminating the need for a central server. Users can directly share and download files from one another, fostering a decentralized network. The system is designed to support multiple concurrent file transfers through an efficient and asynchronous protocol. Decentralized architecture ensures system robustness, while dynamic peer discovery facilitates seamless connections. With a user-friendly interface and enhanced security features, including cryptographic techniques, the system provides a secure, efficient, and decentralized solution, reshaping the landscape of file sharing through peer-to-peer communication.

## Introduction:
In an era dominated by information exchange, traditional file sharing systems often rely on central servers, posing limitations in scalability and resilience. This project introduces a revolutionary Peer-to-Peer (P2P) Distributed File Sharing System, redefining the paradigm by eliminating the need for a central server. Users can directly share and download files from each other's machines, fostering a decentralized network. A key feature of this system is its ability to support multiple concurrent file transfers, optimizing efficiency. This approach not only enhances scalability but also ensures a robust and resilient platform for seamless peer-to-peer file sharing, promising a new era in decentralized data exchange.

## Problem Statement

- Explanation of problem with identification of element/object to be entered through console by the user and the result to be reflected in the form of file content/database/ in the console.
- Highlighting the constraints. 

## Algorithm 
```java
class Peer {
    String ipAddress;
    int port;
    List<String> sharedFiles;
}

class FileTransferTask {
    String sourcePeerIP;
    int sourcePeerPort;
    String fileName;
    String destinationPath;
}

function startListeningThread() {}
function handleFileTransferRequest(Socket connectionSocket, String fileName, String destinationPath) {}
function initiateFileTransfer(String peerIP, int peerPort, String fileName, String destinationPath) {}

function main(){
    Peer currentPeer = new Peer("192.168.0.1", 8001);
    currentPeer.sharedFiles.add("file1.txt");
    currentPeer.sharedFiles.add("file2.txt");
    startListeningThread();
    
    for each peer in otherPeers {
        for each file in currentPeer.sharedFiles {
            initiateFileTransfer(peer.ipAddress, peer.port, file, "downloaded_files/");
        }                                                     }
        for each peer in otherPeers {
            for each file in peer.sharedFiles {
                initiateFileTransfer(peer.ipAddress, peer.port, file, "downloaded_files/");
            }                                                
        }
}

main();
```

## Implementation

### Overview
This project demonstrates a Peer-to-Peer (P2P) Distributed File Sharing System using Java sockets. The system allows peers to share and download files directly with each other without relying on a central server. Each peer can share their local files and initiate downloads from other peers in the network.

### Key Components
1. **Server Component**: 
   - A central server is implemented using `ServerSocket` to listen for incoming connections from peers.
   - Upon connection, the server handles requests to download files from other peers.

2. **Peer Class**:
   - Each peer is represented by the `Peer` class, instantiated with an IP address and port number.
   - Peers can share their local files with other peers by establishing socket connections and transferring files.

3. **File Transfer Mechanism**:
   - Files are transferred over sockets using streams (`InputStream` and `OutputStream`).
   - The system supports concurrent file transfers using a fixed-size thread pool (`ExecutorService`).

### Usage
1. **Starting the Server**:
   - Run the `P2PFileSharingSystem` class to start the server component.
   - The server listens on port `9090` by default for incoming connections from peers.

2. **Initializing Peers**:
   - Create instances of the `Peer` class with appropriate IP addresses and port numbers.
   - Use the `shareFile()` method of each peer to share files with other peers in the network.
   - Use the `downloadFile()` method to initiate downloads from other peers.

3. **File Sharing**:
   - Peers can share files by specifying the file path (`shareFile("path/to/file.txt")`).
   - Downloads are initiated by specifying the remote peer's IP, port, desired file name, and download directory (`downloadFile("remoteIP", port, "file.txt", "downloaded_files/")`).

### Dependencies
- Java Development Kit (JDK) version 8 or higher.

## Conclusion
In conclusion, the implemented P2P Distributed File Sharing System provides a foundation for direct file sharing between users without the need for a central server. The system supports multiple concurrent file transfers, allowing efficient exchange of files among peers. This basic demonstration illustrates the potential for decentralized file sharing, emphasizing the importance of additional features such as robust error handling, security measures, and an improved user interface for a fully functional and secure P2P file-sharing solution. Further development would be needed to enhance the system's reliability and user experience in real-world scenarios.

## References (as per the IEEE recommendations)

- **[1]** Computer Networks, Andrew S. Tannenbaum, Pearson India.
- **[2]** Java Network Programming by Harold, O’Reilly (Shroff Publishers).

