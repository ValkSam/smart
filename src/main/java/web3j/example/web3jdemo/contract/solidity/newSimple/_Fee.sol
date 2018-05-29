pragma solidity ^0.4.23;

import 'browser/_Ownable.sol';
import 'browser/_Fee.sol';
import 'browser/_SafeMath64.sol';
import 'browser/_Arrays.sol';
import "github.com/Arachnid/solidity-stringutils/src/strings.sol";

//4,079,434
//4,462,258
//4,438,050
//4,439,722
//4,701,946
//4,693,962
contract BasicToken is Ownable {

    using SafeMath64 for uint64;
    using strings for *;

    uint64 public maxSupply = uint64(-1);
    uint64 public totalSupply;
    string public name = "Casion";
    string public symbol = "DLT";
    uint public decimals = 2; //TODO

}

contract ManagingUser is BasicToken {

    event UserRegister(
        address indexed userAddress,
        UserKind indexed userKind,
        string details
        );

    event UserTypeSetting(
        address indexed userAddress,
        uint32 indexed prevType,
        uint32 indexed newType
        );

    event UserActiveSetting(
        address indexed userAddress,
        bool indexed acive,
        string details
        );

    enum UserKind {
        ADMIN, USER
    }

    struct UserData {
        bool    active;
        UserKind userKind;
        uint32 userType;
        uint64 balance;
        uint64 balanceReserved;
    }

    uint public usersNumber;
    mapping(address => UserData) internal users;

    function registerAdmin(address userAddress, uint32 userType, string details) external
        onlyOwner
    {
        registerUser(userAddress, UserKind.ADMIN, userType, details);
    }

    function registerUser(address[] userAddresses, uint32 userType, string details, string delimiter) external
        onlyAdmin
    {
        uint i;
        if (bytes(delimiter).length == 0) {
            for (i = 0; i < userAddresses.length; i++) {
                registerUser(userAddresses[i], UserKind.USER, userType, details);
            }
        } else {
            var detailsSlice = details.toSlice();
            var delim = delimiter.toSlice();
            require(userAddresses.length == detailsSlice.count(delim) + 1);

            for (i = 0; i < userAddresses.length; i++) {
                registerUser(userAddresses[i], UserKind.USER, userType, detailsSlice.split(delim).toString());
            }
        }
    }

    function registerUser(address userAddress, UserKind userKind, uint32 userType, string details) internal {
        require(userAddress != address(0));
        require(userType != 0);
        require(users[userAddress].userType == 0);

        users[userAddress] = UserData(true, userKind, userType, 0, 0);

        usersNumber++;

        emit UserRegister (
            userAddress,
            userKind,
            details
            );

        emit UserTypeSetting (
            userAddress,
            0,
            userType
            );
    }

    function setUserTypeFor(address userAddress, uint32 userType) external
        onlyAdmin
    {
        uint32 currUserType = users[userAddress].userType;
        require(currUserType != userType);
        users[userAddress].userType = userType;

        emit UserTypeSetting (
            userAddress,
            currUserType,
            userType
            );
    }

    function activateUser(address userAddress, string details) external
        onlyAdmin
    {
        setUserActiveFor(userAddress, true, details);
    }

    function deActivateUser(address userAddress, string details) external
        onlyAdmin
    {
        setUserActiveFor(userAddress, false, details);
    }

    function setUserActiveFor(address userAddress, bool active, string details) internal {
        require(users[userAddress].active != active);
        users[userAddress].active = active;

        emit UserActiveSetting (
            userAddress,
            active,
            details
            );
    }

    /*public*/

    function typeOf(address _address) external view
        returns(uint)
    {
        return users[_address].userType;
    }

    function activeOf(address _address) public view
        returns(bool)
    {
        return users[_address].active;
    }

    /*modifier*/

    modifier onlyAdmin() {
        if (msg.sender != owner) {
            require (users[msg.sender].userKind == UserKind.ADMIN);
        }
        _;
    }

}

contract ManagingFee is Ownable {

     Fee internal fee;

     function registerFeeContract(address feeContractAddress) external
        onlyOwner
     {
         fee = Fee(feeContractAddress);
         require(fee.activated());
     }
}

contract Documenting {

    using Arrays for bytes32[];
    using Strings for string;

    mapping(address=>mapping(bytes32=>uint64)) internal docLogs;
    mapping(address=>bytes32[]) internal docLogsList;

    function documentsUIDs(address _address) public view
        returns (bytes32[] documentUIDs)
    {
        return docLogsList[_address];
    }

    function documentExists(address _address, string documentUID) public view
        returns(bool)
    {
        return documentAmount(_address, documentUID) != 0;
    }

    function documentAmount(address _address, string documentUID) public view
        returns(uint64)
    {
        return docLogs[_address][documentHash(documentUID)];
    }

    function addDocument(address _address, uint64 amount, string documentUID) internal
    {
        require(bytes(documentUID).length != 0);
        require(bytes(documentUID).length <= 32);
        require(amount > 0);
        require(!documentExists(_address, documentUID));
        bytes32 documentUIDHash = documentHash(documentUID);
        docLogs[_address][documentUIDHash] = amount;
        docLogsList[_address].push(documentUIDHash);
    }

    function removeDocument(address _address, string documentUID) internal
    {
        require(documentExists(_address, documentUID));
        bytes32 documentUIDHash = documentHash(documentUID);
        uint idx = docLogsList[_address].find(documentUIDHash);
        assert(idx != uint(-1));
        docLogsList[_address].remove(idx);
        delete docLogs[_address][documentUIDHash];
    }

    function documentHash(string document) internal pure
        returns(bytes32)
    {
        return document.stringToBytes32();
    }
}

contract OperatingUser is ManagingUser {

    event Transfer(
        address indexed from,
        address indexed to,
        uint amount,
        uint fee,
        string details
        );

    function incBalance(address userAddress, uint64 amount) internal
        onlyForActiveUser(userAddress)
    {
        users[userAddress].balance = users[userAddress].balance.add(amount);
    }

    function decBalance(address userAddress, uint64 amount) internal
        onlyForActiveUser(userAddress)
    {
        users[userAddress].balance = users[userAddress].balance.sub(amount);
    }

    function incBalanceReserved(address userAddress, uint64 amount) internal
        onlyForActiveUser(userAddress)
    {
        users[userAddress].balanceReserved = users[userAddress].balanceReserved.add(amount);
    }

    function decBalanceReserved(address userAddress, uint64 amount) internal
        onlyForActiveUser(userAddress)
    {
        users[userAddress].balanceReserved = users[userAddress].balanceReserved.sub(amount);
    }

    modifier onlyForActiveUser(address userAddress) {
        require (activeOf(userAddress));
        _;
    }

}

contract Enrolling is Documenting, OperatingUser {

    event RegisterEnrollDocument(
        address indexed userAddress,
        string indexed documentUID,
        uint64 amount,
        string details
        );

    event Mint(
        address userAddress,
        uint64 amount,
        string indexed documentUID,
        string details
        );

    function registerEnrollRequest(address userAddress, uint64 amount, string documentUID, string details) external
        onlyAdmin
    {
        addDocument(userAddress, amount, documentUID);

        emit RegisterEnrollDocument(
            userAddress,
            documentUID,
            amount,
            details);
    }

    function enroll(address userAddress, string documentUID, string details) external
        onlyAdmin
    {
        uint64 amount = documentAmount(userAddress, documentUID);
        require(totalSupply + amount <= maxSupply);
        totalSupply = totalSupply.add(amount);

        removeDocument(userAddress, documentUID);

        incBalance(userAddress, amount);

        emit Mint(
            userAddress,
            amount,
            documentUID,
            details);

        emit Transfer(
            0,
            userAddress,
            amount,
            0,
            details
            );
    }

}

contract Withdrowing is Documenting, OperatingUser {

    event RegisterWithdrawDocument(
        address indexed userAddress,
        string indexed documentUID,
        uint64 amount,
        string details
        );

    event Burn(
        address indexed userAddress,
        uint64 amount,
        string indexed documentUID,
        string details
        );

    function registerWithdrawRequest(address userAddress, uint64 amount, string documentUID, string details) external
        onlyAdmin
    {
        addDocument(userAddress, amount, documentUID);

        decBalance(userAddress, amount);
        incBalanceReserved(userAddress, amount);

        emit RegisterWithdrawDocument(
            userAddress,
            documentUID,
            amount,
            details);
    }

    function withdraw(address userAddress, string documentUID, string details) external
        onlyAdmin
    {
        uint64 amount = documentAmount(userAddress, documentUID);
        totalSupply = totalSupply.sub(amount);

        removeDocument(userAddress, documentUID);

        decBalanceReserved(userAddress, amount);

        emit Burn(
            userAddress,
            amount,
            documentUID,
            details);

        emit Transfer(
            userAddress,
            0,
            amount,
            0,
            details
            );
    }

}

contract Casion is Enrolling, Withdrowing, ManagingFee {

    function balanceOf(address _address) external view
        returns (uint)
    {
        return users[_address].balance;
    }

    function balanceResirvedOf(address _address) external view
        returns (uint)
    {
        return users[_address].balanceReserved;
    }

    function transfer(address receiver, uint64 amount, string details) external
        onlyForActiveUser(msg.sender)
        onlyForActiveUser(receiver)
    {
        transferFromTo(msg.sender, receiver, amount, details);
    }

    function transferFrom(address[] senders, address[] receivers, uint64[] amounts, string details, string delimiter) external
        onlyAdmin
    {
        require(senders.length == 1 || senders.length == receivers.length);
        require(receivers.length == 1 || receivers.length == senders.length);
        require(amounts.length == 1 || amounts.length == senders.length || amounts.length == receivers.length);

        uint i;
        if (bytes(delimiter).length == 0) {
            for (i = 0; i < senders.length && i < receivers.length; i++) {
                transferFromTo(
                    senders[i < senders.length ? i : 0],
                    receivers[i < receivers.length ? i : 0],
                    amounts[i < amounts.length ? i : 0],
                    details);
            }
        } else {
            var detailsSlice = details.toSlice();
            var delim = delimiter.toSlice();
            require(senders.length == detailsSlice.count(delim) + 1
                    ||receivers.length == detailsSlice.count(delim) + 1);

            for (i = 0; i < senders.length && i < receivers.length; i++) {
                transferFromTo(
                    senders[i < senders.length ? i : 0],
                    receivers[i < receivers.length ? i : 0],
                    amounts[i < amounts.length ? i : 0],
                    detailsSlice.split(delim).toString());
            }
        }
    }

    function transferFromTo(address from, address to, uint64 amount, string details) private  {
        uint64 feeValue = getFeeValue(
                msg.sender,
                from,
                to,
                amount
        );
        require(amount.add(feeValue) <= users[from].balance);
        users[from].balance = users[from].balance.sub(amount.add(feeValue));
        users[to].balance = users[to].balance.add(amount);

        emit Transfer(
            from,
            to,
            amount,
            feeValue,
            details
            );
    }

    function getFeeValue(address initiator, address sender, address receiver, uint64 amount) public view returns(uint64 result){
        result = fee.countFee(
                users[initiator].userType,
                users[sender].userType,
                users[receiver].userType,
                amount
            );
        assert(result != uint64(-1));
    }

}