import AWS from 'aws-sdk'
import { log2CloudWatch , error2CloudWatch } from './cloudWatch'
export interface resultMessageResponseType {
    /** Whether the request is successful */
    result: boolean,
    /** Response information */
    message: string
  }

type createLeaveType = (
    userId : string,
    startEndDate : string,
    leaveType : string,
    approver : string,
    remarks: string,
    status: string,
  ) => Promise <resultMessageResponseType>
  
  /**
   * Accesses the main table and insert user leave.
   * @param userId - User id
   * @param startEndDate - Start date and end date of applied leave
   * @param leaveType - Type of leave - AL,MC,OIL
   * @param approver - The user id of the person who going to approve the leave
   * @param remarks - Remarks on the applied leave
   * @param status - Status of leave - Pending,Approved,Rejected
   * @returns resultMessageResponseType
   */
  export const createLeave : createLeaveType = async (
    userId,
    startEndDate,
    leaveType,
    approver,
    remarks,
    status,
  ) => {
    const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' });

    const params = {
        Item: {
          'PK': { S: `USER#${userId}` },
          'SK': { S: `LEAVE#${startEndDate}` },
          'LeaveType': { S: leaveType },
          'Approver': { S: approver },
          'Remarks': { S: remarks },
          'Status': { S: status },
        },
        TableName: 'mainTable'
      }
    
      let message = '';     
      try {
        await dynamodb.putItem(params).promise();
        message = `User leave successfully created.`;
        log2CloudWatch("mainTable.ts","createLeave",message)
        return {
          'result': true,
          message
        };
      } catch (err) {
        message = `User leave had failed to be created.`;
        error2CloudWatch("mainTable.ts","createLeave",err)
        return {
          'result': false,
          message
        };
      }
    } 
