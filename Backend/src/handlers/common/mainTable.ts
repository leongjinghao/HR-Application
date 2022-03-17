/* eslint-disable no-console */
import AWS from 'aws-sdk'
import { log2CloudWatch , error2CloudWatch } from '../utility/cloudWatch'
import { resultMessageResponseTypeDatabase } from '../utility/dataType'

type createLeaveType = (
    userId : string,
    startEndDate : string,
    leaveType : string,
    approver : string,
    remarks: string,
    status: string,
  ) => Promise <resultMessageResponseTypeDatabase>
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
  export const putCreateLeave : createLeaveType = async (
    userId,
    startEndDate,
    leaveType,
    approver,
    remarks,
    status,
  ) => {
    const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })

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
      let message = ''
      try {
        await dynamodb.putItem(params).promise()
        message = 'User leave successfully created.'
        log2CloudWatch('mainTable.ts','createLeave',message)
        return {
          'result': true,
          message
        }
      } catch (err) {
        message = 'User leave had failed to be created.'
        error2CloudWatch('mainTable.ts','createLeave',err)
        return {
          'result': false,
          message
        }
      }
    }

    type queryUserLeaveInformation = (
      userId : string,
    ) => Promise<{PK:string,SK:string,LeaveType:string,Approver:string,Remarks:string,Status:string,} | false | {}>
    /**
     * Access the main Table and retrieve all Users Leave
     * @param {string} userId User ID
     * @returns {Promise <{PK:string,SK:string,LeaveType:string,Approver:string,Remarks:string,Status:string,}
     * | false | {}> } User Leaves Informations
     */
    export const queryUserLeaveInformation: queryUserLeaveInformation = async (userId) => {
      const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })
      const params: AWS.DynamoDB.QueryInput = {
        TableName: 'mainTable',
        KeyConditionExpression: '#PK = :PK AND begins_with(#SK, :SK)',
        ExpressionAttributeNames: {
          '#PK': 'PK',
          '#SK': 'SK'
        },
        ExpressionAttributeValues: {
          ':PK': { S: `USER#${userId}` },
          ':SK': { S: 'LEAVE#' }
        }
      }
      try {
        const data = await dynamodb.query(params).promise()
        console.log(data)
        return data
      } catch (err) {
        console.log(err)
        return false
      }
    }

    type queryUserInformation = (
      userId : string,
    ) => Promise<{
      PK:string,
      SK:string,
      Name:string,
      DOB:string,
      Mobile:string,
      Email:string,
      Department:string,
      Picture:string,
      Al:string,
      MC:string,
      OIL:string} | false | {}>
    /**
     * Access the main Table and retrieve all User Information based on User Id
     * @param {string} userId User ID
     * @returns {Promise <{
     * PK:string,
     * SK:string,
     * Name:string,
     * DOB:string,
     * Mobile:string,
     * Email:string,
     * Department:string,
     * Picture:string,
     * Al:string,
     * MC:string,
     * OIL:string}
     * | false | {}> } User Information
     */
    export const queryUserInformation: queryUserInformation = async (userId) => {
      const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })
      const params: AWS.DynamoDB.QueryInput = {
        TableName: 'mainTable',
        KeyConditionExpression: '#PK = :PK AND begins_with(#SK, :SK)',
        ExpressionAttributeNames: {
          '#PK': 'PK',
          '#SK': 'SK'
        },
        ExpressionAttributeValues: {
          ':PK': { S: `USER#${userId}` },
          ':SK': { S: `PROFILE#${userId}` }
        }
      }
      try {
        const data = await dynamodb.query(params).promise()
        return data
      } catch (err) {
        return false
      }
    }

