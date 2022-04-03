/* eslint-disable no-console */
import AWS from 'aws-sdk'
import { log2CloudWatch, error2CloudWatch } from '../utility/cloudWatch'
import { resultMessageResponseTypeDatabase } from '../utility/dataType'

type createLeaveType = (
  userId: string,
  startEndDate: string,
  leaveType: string,
  approver: string,
  remarks: string,
  status: string,
) => Promise<resultMessageResponseTypeDatabase>
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
export const putCreateLeave: createLeaveType = async (
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
    log2CloudWatch('mainTable.ts', 'createLeave', message)
    return {
      'result': true,
      message
    }
  } catch (err) {
    message = 'User leave had failed to be created.'
    error2CloudWatch('mainTable.ts', 'createLeave', err)
    return {
      'result': false,
      message
    }
  }
}

    type queryUserLeaveInformationType = (
      userId : string,
    ) => Promise<[{PK:{S:string},SK:{S:string},LeaveType:{S:string},Approver:{S:string},Remarks:{S:string},
      Status:{S:string}}] | false | []>
    /**
     * Access the main Table and retrieve all Users Leave
     * @param {string} userId User ID
     * @returns {Promise <{PK:string,SK:string,LeaveType:string,Approver:string,Remarks:string,Status:string,}
     * | false | {}> } User Leaves Informations
     */
    export const queryUserLeaveInformation: queryUserLeaveInformationType = async (userId) => {
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
        let response
        if (data.Count !== 0) {
          response = data.Items!
        }
        return response
      } catch (err) {
        return false
      }
    }

    type removeUserLeaveInformationType = (
      userId : string,
      date : string,
    ) => Promise<resultMessageResponseTypeDatabase>
    /**
     * Access the main Table and remove the user Leave
     * @param {string} userId User ID
     * @param {string} date User Leave
     * @returns {Promise <{resultMessageResponseTypeDatabase}
     */
    export const deleteUserLeaveInformation: removeUserLeaveInformationType = async (userId,date) => {
      const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })
      const params = {
        TableName: 'mainTable',
        Key: {
          ':PK': { S: `USER#${userId}` },
          ':SK': { S: `LEAVE#${date}` }
        }
      }
      try {
        await dynamodb.deleteItem(params).promise()
        return {
          'result': true,
          message : `${userId} leave on ${date} successfully deleted from the database.`
        }
      } catch (err) {
        return {
          'result': false,
          message: JSON.stringify(err)
        }
      }
    }

    type updateLeaveStatusType = (
      userId : string,
      dob : string,
      status : string,
    ) => Promise <resultMessageResponseTypeDatabase>
    /**
     * Update user information
     * @param userId - Employee ID
     * @param dob - Leave Date
     * @param status - Leave Status
     * @returns resultMessageResponseType
     */
    export const updateLeaveStatus : updateLeaveStatusType = async (
      userId,
      dob,
      status,
    ) => {
      const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })
      const params = {
          Key: {
            'PK': { S: `USER#${userId}` },
            'SK': { S: `LEAVE#${dob}` },
          },
          TableName: 'mainTable',
          UpdateExpression:
          'set LeaveStatus = :status',
          ExpressionAttributeValues: {
            ':status': { S: status }
      }
        }
        let message = ''
        try {
          await dynamodb.updateItem(params).promise()
          message = 'Leave status had been updated successfully.'
          log2CloudWatch('mainTable.ts','updateUserInformation',message)
          return {
            'result': true,
            message
          }
        } catch (err) {
          message = 'Leave Status failed to update.'
          error2CloudWatch('mainTable.ts','updateUserInformation',err)
          return {
            'result': false,
            message
          }
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

type queryUserLoginType = (
  userEmail: string, userPassword: string
) => Promise<[{ PK: string, SK: string, UserId: string }] | false | [{}]>
/**
 * Access the main Table and retrieve all Users Leave
 * @param {string} userEmail User Email
 * @param {string} userPassword User Password
 * @returns {Promise <[{PK:string,SK:string,UserId:string,}]
 * | {} } User Leaves Informations
 */
export const queryUserLogin = async (userEmail, userPassword) => {
  const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })
  const params: AWS.DynamoDB.QueryInput = {
    TableName: 'mainTable',
    KeyConditionExpression: '#PK = :PK AND #SK = :SK',
    ExpressionAttributeNames: {
      '#PK': 'PK',
      '#SK': 'SK'
    },
    ExpressionAttributeValues: {
      ':PK': { S: `LOGIN#${userEmail}` },
      ':SK': { S: `PASSWORD#${userPassword}` }
    }
  }
  try {
    const data = await dynamodb.query(params).promise()
    return data.Items!
  } catch (err) {
    console.log(err)
    return []
  }
}

type createAttendanceInfo = (
  userId: string,
  date: string,
  clockIn: string,
  location: string,
) => Promise<resultMessageResponseTypeDatabase>
/**
 * Accesses the main table and insert user leave.
 * @param userId - User id
 * @param date - Attendance date
 * @param clockIn - Clock in timing
 * @param location - location description
 * @returns resultMessageResponseType
 */
export const putNewAttendanceInfo: createAttendanceInfo = async (
  userId,
  date,
  clockIn,
  location,
) => {
  const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })

  const params = {
    Item: {
      'PK': { S: `USER#${userId}` },
      'SK': { S: `ATTENDANCE#${date}` },
      'ClockIn': { S: clockIn },
      'ClockOut': { S: '' },
      'Location': { S: location },
    },
    TableName: 'mainTable'
  }
  let message = ''
  try {
    await dynamodb.putItem(params).promise()
    message = 'User attendance successfully created.'
    log2CloudWatch('mainTable.ts', 'createLeave', message)
    return {
      'result': true,
      message
    }
  } catch (err) {
    message = 'User attendance had failed to be created.'
    error2CloudWatch('mainTable.ts', 'createLeave', err)
    return {
      'result': false,
      message
    }
  }
}

type updateAttendanceInfo = (
  userId: string,
  date: string,
  clockIn: string,
  clockOut: string,
  location: string,
) => Promise<resultMessageResponseTypeDatabase>
/**
 * Accesses the main table and insert user leave.
 * @param userId - User id
 * @param date - Attendance date
 * @param clockIn - Clock in timing
 * @param clockOut - Clock out timing
 * @param location - location description
 * @returns resultMessageResponseType
 */
export const putExistingAttendanceInfo: updateAttendanceInfo = async (
  userId,
  date,
  clockOut,
) => {
  const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })

  const params = {
    Item: {
      'PK': { S: `USER#${userId}` },
      'SK': { S: `ATTENDANCE#${date}` },
      'ClockOut': { S: clockOut },
    },
    TableName: 'mainTable'
  }
  let message = ''
  try {
    await dynamodb.putItem(params).promise()
    message = 'User clock out attendance successfully updated.'
    log2CloudWatch('mainTable.ts', 'createLeave', message)
    return {
      'result': true,
      message
    }
  } catch (err) {
    message = 'User clock out attendance had failed to be updated.'
    error2CloudWatch('mainTable.ts', 'createLeave', err)
    return {
      'result': false,
      message
    }
  }
}

type putUserInformationType = (
  userId : string,
  name : string,
  department : string,
  dateofbirth : string,
  phonenumber : string,
  email : string
) => Promise <resultMessageResponseTypeDatabase>
/**
 * Update user information
 * @param userId - Employee ID
 * @param name - Employee Name
 * @param department - Department Name
 * @param dateofbirth - Date of Birth
 * @param phonenumber - Handphone Number
 * @param email - Email Address
 * @returns resultMessageResponseType
 */
export const putUserInformation : putUserInformationType = async (
  userId,
  department,
  name,
  dateofbirth,
  phonenumber,
  email
) => {
  const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })
  const params = {
      Key: {
        'PK': { S: `USER#${userId}` },
        'SK': { S: `PROFILE#${userId}` },
      },
      TableName: 'mainTable',
      UpdateExpression:
      'set EmployeeName = :name, Department = :department, DOB = :dateofbirth, Mobile = :phonenumber, Email = :email',
      ExpressionAttributeValues: {
        ':name': { S: name },
        ':department' : { S: department},
        ':dateofbirth': { S: dateofbirth },
        ':phonenumber': { S: phonenumber },
        ':email': { S: email }
  }
    }
    let message = ''
    try {
      await dynamodb.updateItem(params).promise()
      message = 'Updated user Information.'
      log2CloudWatch('mainTable.ts','updateUserInformation',message)
      return {
        'result': true,
        message
      }
    } catch (err) {
      message = 'Failed to update user information.'
      error2CloudWatch('mainTable.ts','updateUserInformation',err)
      return {
        'result': false,
        message
      }
    }
  }
