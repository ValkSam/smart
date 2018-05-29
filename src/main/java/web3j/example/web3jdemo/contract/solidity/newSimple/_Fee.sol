pragma solidity ^0.4.23;

import 'browser/_Ownable.sol';
import 'browser/_Math.sol';

contract Fee {

    using Math for uint64;

    struct FeeRule {
        uint32 userTypeSender;
        uint32 userTypeReceiver;
        uint32 percent;
        uint48 fixedAmount;
        uint48 minAmount;
        uint64 maxAmount;
    }

    uint64 private max = uint64(-1);

    FeeRule[] private feeRules;

    bool public activated = false;
    FeeRule[] public wrongRules;
    mapping(uint64=>bool) private feeRuleKeys;

    function init() external {
        require(feeRules.length == 0);
        feeRules.push(FeeRule(2001, 2002, 1, 3, 10, max));

        feeRules.push(FeeRule(2000, 2001, 2, 3, 10, max));          //feeRules.push(FeeRule(0, 2001, 2, 3, 10, max56));
        feeRules.push(FeeRule(2002, 2000, 2, 3, 10, 20));         //feeRules.push(FeeRule(2002, 0, 2, 3, 10, 20));

        feeRules.push(FeeRule(2004, 2001, 2, 3, 20, 30));         //feeRules.push(FeeRule(2004, 2001, 2, 3, 20, 10));
        feeRules.push(FeeRule(2004, 2004, 2, 3, 10, max));      //good
        feeRules.push(FeeRule(2004, 2005, 2, 3, 10, 20));       //good

        feeRules.push(FeeRule(2002, 2006, 0, 30, 10, 40));      //feeRules.push(FeeRule(2002, 2006, 0, 30, 10, 20));

        feeRules.push(FeeRule(2003, 2006, 2, 3, 10, 20));   //good
        feeRules.push(FeeRule(2003, 2007, 2, 3, 10, 20));   //good
        // feeRules.push(FeeRule(2003, 2007, 2, 4, 20, 30));       //double
    }

    function wrongRulesNumbers() external view
        returns(uint)
    {
        return wrongRules.length;
    }

    function countFee(uint32 initiator, uint32  userTypeSender, uint32 userTypeReceiver, uint64 amount) external view returns(uint64 result) {
        require(activated);
        FeeRule memory feeRule = getFeeRule(userTypeSender, userTypeReceiver);
        if (feeRule.userTypeSender == 0) {
            return uint64(-1);
        }
        result = amount * feeRule.percent / 100 + feeRule.fixedAmount;
        return result.max64(feeRule.minAmount).min64(feeRule.maxAmount);
    }

    function getFeeRule(uint32 userTypeSender, uint32 userTypeReceiver) private view returns(FeeRule) {
        for (uint i = 0; i < feeRules.length; i++) {
            if (feeRules[i].userTypeSender == userTypeSender && feeRules[i].userTypeReceiver == userTypeReceiver) {
                return feeRules[i];
            }
        }
    }

    function checkRuls() external
        returns(uint)
    {
        require(feeRules.length > 0);
        checkDoubleRules();
        checkRuleParams();
        activated = wrongRules.length == 0;
    }

    function checkRuleParams() private {

        for (uint i = 0; i < feeRules.length; i++) {
            if (feeRules[i].userTypeSender == 0 || feeRules[i].userTypeReceiver == 0) {
                wrongRules.push(feeRules[i]);
            }
            if (feeRules[i].minAmount > feeRules[i].maxAmount) {
                wrongRules.push(feeRules[i]);
            }
            if (feeRules[i].fixedAmount > feeRules[i].maxAmount) {
                wrongRules.push(feeRules[i]);
            }
        }
    }

     function checkDoubleRules() private {
        for (uint i = 0; i < feeRules.length; i++) {
            uint64 feeRuleKey = (uint64(feeRules[i].userTypeSender) << 32)
                        | uint64(feeRules[i].userTypeReceiver);
            if (feeRuleKeys[feeRuleKey]) {
                wrongRules.push(feeRules[i]);
            } else {
                feeRuleKeys[feeRuleKey] = true;
            }
        }
     }

}