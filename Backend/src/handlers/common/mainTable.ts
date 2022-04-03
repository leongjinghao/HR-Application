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
      LeaveStatus:{S:string}}] | false | []>
    /**
     * Access the main Table and retrieve all Users Leave
     * @param {string} userId User ID
     * @returns {Promise <{PK:string,SK:string,LeaveType:string,Approver:string,Remarks:string,LeaveStatus:string,}
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
          'PK': { S: `USER#${userId}`},
          'SK': { S: `LEAVE#${date}`}
        }
      }
      try {
        await dynamodb.deleteItem(params).promise()
        return {
          'result': true,
          message : `${userId} leave on ${date} had successfully cancel`
        }
      } catch (err) {
        console.log(err)
        return {
          'result': false,
          message: `${userId} leave on ${date} fail to cancel`
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
        try {
          await dynamodb.updateItem(params).promise()
          log2CloudWatch('mainTable.ts','updateUserInformation','Leave status had been updated successfully.')
          return {
            'result': true,
            message : 'Leave status had been updated successfully.'
          }
        } catch (err) {
          error2CloudWatch('mainTable.ts','updateUserInformation',err)
          return {
            'result': false,
            message : 'Leave Status failed to update.'
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
     * EmployeeName:string,
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
        KeyConditionExpression: '#PK = :PK AND #SK = :SK',
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

  const queryParams: AWS.DynamoDB.QueryInput = {
    TableName: 'mainTable',
    KeyConditionExpression: '#PK = :PK AND #SK = :SK',
    ExpressionAttributeNames: {
      '#PK': 'PK',
      '#SK': 'SK'
    },
    ExpressionAttributeValues: {
      ':PK': { S: `USER#${userId}` },
      ':SK': { S: `ATTENDANCE#${date}` }
    }
  }

  let message = ''
  try {
    const queryResult = await dynamodb.query(queryParams).promise()
    if (queryResult.Items!.length !== 1) {
      await dynamodb.putItem(params).promise()
      message = 'User attendance successfully created.'
      log2CloudWatch('mainTable.ts', 'createAttendance', message)
      return {
        'result': true,
        message
      }
    } else {
      return {
        'result': false,
        'message': 'Already checked in for the day'
      }
    }
  } catch (err) {
    message = 'User attendance had failed to be created.'
    error2CloudWatch('mainTable.ts', 'createAttendance', err)
    return {
      'result': false,
      message
    }
  }
}

type updateAttendanceInfo = (
  userId: string,
  date: string,
  clockOut: string,
) => Promise<resultMessageResponseTypeDatabase>
/**
 * Accesses the main table and insert user leave.
 * @param userId - User id
 * @param date - Attendance date
 * @param clockOut - Clock out timing
 * @returns resultMessageResponseType
 */
export const putExistingAttendanceInfo: updateAttendanceInfo = async (
  userId,
  date,
  clockOut,
) => {
  const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })

  const params = {
    Key: {
      'PK': { S: `USER#${userId}` },
      'SK': { S: `ATTENDANCE#${date}` },
    },
    TableName: 'mainTable',
    UpdateExpression:
      'set ClockOut = :clockOut',
    ExpressionAttributeValues: {
      ':clockOut': { S: clockOut }
    }
  }
  let message = ''
  try {
    await dynamodb.updateItem(params).promise()
    message = 'User clock out attendance successfully updated.'
    log2CloudWatch('mainTable.ts', 'updateAttendance', message)
    return {
      'result': true,
      message
    }
  } catch (err) {
    message = 'User clock out attendance had failed to be updated.'
    error2CloudWatch('mainTable.ts', 'updateAttendance', err)
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

  type queryPassword = (
    userId: string
  ) => Promise<[{ PK: string, SK: string, UserId: string }] | false | [{}]>
  /**
   * Access the main Table and retrieve login details based on UserId
   * @param {string} userId User Id
   * @returns {Promise <[{PK:string,SK:string,UserId:string,}]
   * | {} } User Password Informations
   */
  export const queryPassword = async (userId) => {
    const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })
    const params: AWS.DynamoDB.QueryInput = {
      TableName: 'mainTable',
      IndexName: 'UserIdIndex',
      KeyConditionExpression: '#UserId = :UserId',
      ExpressionAttributeNames: {
        '#UserId': ':UserId',
      },
      ExpressionAttributeValues: {
        ':UserId': userId
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

  type putUserPasswordType = (
    userId : string,
    newPassword : string
  ) => Promise <resultMessageResponseTypeDatabase>
  /**
   * Update user password
   * @param userId - Employee ID
   * @param newPassword - New Password
   * @returns resultMessageResponseType
   */
  export const putUserPassword : putUserPasswordType = async (
    userId,
    newPassword
  ) => {
    const dynamodb = new AWS.DynamoDB({ region: 'ap-southeast-1', apiVersion: '2012-08-10' })
    const params = {
      IndexName: 'UserIdIndex',
      Key: {
        'UserId': { S: `${userId}` },
      },
      TableName: 'mainTable',
      UpdateExpression:
      'set SK = :sk',
      ExpressionAttributeValues: {
      ':sk': { S: `PASSWORD#${newPassword}` },
  }
    }
      let message = ''
      try {
        await dynamodb.updateItem(params).promise()
        message = 'Updated user password.'
        log2CloudWatch('mainTable.ts','putUserPassword',message)
        return {
          'result': true,
          message
        }
      } catch (err) {
        message = 'Failed to update user password.'
        error2CloudWatch('mainTable.ts','putUserPassword',err)
        return {
          'result': false,
          message
        }
      }
    }
