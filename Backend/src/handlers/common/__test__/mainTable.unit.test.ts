/* eslint-disable no-console */
import { putCreateLeave,
    queryUserLeaveInformation,
    deleteUserLeaveInformation,
    updateLeaveStatus,
    queryUserInformation,
    queryUserDepartment,
    queryUserInformationByDepartment,
    queryUserLogin,
    putNewAttendanceInfo,
    putExistingAttendanceInfo,
    putUserInformation,
    queryPassword,
    putUserPassword,
    queryUserSchedule,
    queryUserEmail
     } from '../mainTable'
import { resultMessageResponseTypeDatabase } from '../../utility/dataType'
import AWS from 'aws-sdk'
import { mockLeaveInformation,
    mockLeaveInformationExtractFromItems,
    mockUserInformation,
    mockUserInformationExtractFromItems,
    mockUserLoginInformation,
    mockUserLoginInformationExtractFromItems,
    mockAttendanceInformation,
    mockScheduleInformation,
    mockScheduleInformationExtractFromItems } from './mockData'

console.log = jest.fn
console.error = jest.fn

jest.mock('aws-sdk', () => {
    const dynamoDbQueryPromiseMock = jest.fn()
      .mockResolvedValue({})
    const dynamoDbPutItemPromiseMock = jest.fn()
      .mockResolvedValue(true)
    const dynamoDbUpdateItemPromiseMock = jest.fn()
      .mockResolvedValue(true)
    const dynamoDbDeleteItemPromiseMock = jest.fn()
      .mockResolvedValue(true)
    return {
      DynamoDB: jest.fn(() => ({
        query: () => ({
          promise: dynamoDbQueryPromiseMock,
        }),
        putItem: () => ({
          promise: dynamoDbPutItemPromiseMock,
        }),
        updateItem: () => ({
          promise: dynamoDbUpdateItemPromiseMock,
        }),
        deleteItem: () => ({
          promise: dynamoDbDeleteItemPromiseMock,
        })
      }),
      )}
  })

beforeEach(() => {
    jest.clearAllMocks()
  })

const dynamodb = new AWS.DynamoDB({})

describe('/common/mainTable mainTable.unit.ts', () => {
    test('Successfully insert data into MainTable, function putCreateLeave', async () => {
        (dynamodb.putItem().promise as jest.Mock).mockResolvedValueOnce(true)
            const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'User leave successfully created.'
        }
        const response = await putCreateLeave(
            'mockUserId',
            'mockStartEndDate',
            'mockLeaveType',
            'mockApprover',
            'mockRemarks',
            'mockStatus')
        expect(response.result).toStrictEqual(matchResponse.result)
        expect(response.message).toStrictEqual(matchResponse.message)
    })

    test('Error Inserting Data into mainTable, function putCreateLeave', async () => {
        (dynamodb.putItem().promise as jest.Mock).mockRejectedValueOnce(false)
            const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'User leave had failed to be created.'
        }
        const response = await putCreateLeave(
            'mockUserId',
            'mockStartEndDate',
            'mockLeaveType',
            'mockApprover',
            'mockRemarks',
            'mockStatus')
        expect(response.result).toStrictEqual(matchResponse.result)
        expect(response.message).toStrictEqual(matchResponse.message)
    })

    test('Successfully retrieve user leave information, function queryUserLeaveInformation', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce(mockLeaveInformation)
        const response = await queryUserLeaveInformation('mockUserId')
        expect(response).toStrictEqual(mockLeaveInformationExtractFromItems)
    })

    test('Error retrieve user leave information, function queryUserLeaveInformation', async () => {
        (dynamodb.query().promise as jest.Mock).mockRejectedValueOnce(false)
        const response = await queryUserLeaveInformation('mockUserId')
        expect(response).toStrictEqual(false)
    })

    test('Delete user leave information, function deleteUserLeaveInformation', async () => {
        (dynamodb.deleteItem().promise as jest.Mock).mockResolvedValueOnce(true)
        const response = await deleteUserLeaveInformation('mockUserId','mockDate')
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'mockUserId leave on mockDate had successfully cancel'}
        expect(response).toStrictEqual(matchResponse)
    })

    test('Error deleting user leave information, function deleteUserLeaveInformation', async () => {
        (dynamodb.deleteItem().promise as jest.Mock).mockRejectedValueOnce(false)
        const response = await deleteUserLeaveInformation('mockUserId','mockDate')
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'mockUserId leave on mockDate fail to cancel'}
        expect(response).toStrictEqual(matchResponse)
    })

    test('Successfully update user leave status, function updateLeaveStatus', async () => {
        (dynamodb.updateItem().promise as jest.Mock).mockResolvedValueOnce(true)
            const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'Leave status had been updated successfully.'
        }
        const response = await updateLeaveStatus(
            'mockUserId',
            'mockDob',
            'mockLeaveStatus'
            )
        expect(response.result).toStrictEqual(matchResponse.result)
        expect(response.message).toStrictEqual(matchResponse.message)
    })

    test('Error updating user leave status, function updateLeaveStatus', async () => {
        (dynamodb.updateItem().promise as jest.Mock).mockRejectedValueOnce(false)
            const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'Leave Status failed to update.'
        }
        const response = await updateLeaveStatus(
            'mockUserId',
            'mockDob',
            'mockLeaveStatus'
            )
        expect(response.result).toStrictEqual(matchResponse.result)
        expect(response.message).toStrictEqual(matchResponse.message)
    })

    test('Successfully retrieve user information, function queryUserInformation', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce(mockUserInformation)
        const response = await queryUserInformation('mockUserId')
        expect(response).toStrictEqual(mockUserInformation)
    })

    test('Error retrieving user information, function queryUserInformation', async () => {
        (dynamodb.query().promise as jest.Mock).mockRejectedValueOnce(false)
        const response = await queryUserInformation('mockUserId')
        expect(response).toStrictEqual(false)
    })

    test('Successfully retrieve user department, function queryUserDepartment', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce(mockUserInformation)
        const response = await queryUserDepartment('mockUserId')
        expect(response).toStrictEqual('mockDepartment')
    })

    test('Error retrieving user department, function queryUserDepartment', async () => {
        (dynamodb.query().promise as jest.Mock).mockRejectedValueOnce(false)
        const response = await queryUserDepartment('mockUserId')
        expect(response).toStrictEqual(false)
    })

    test('Successfully retrieve user info based on department, function queryUserInformationByDepartment', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce(mockUserInformation)
        const response = await queryUserInformationByDepartment('mockDepartment')
        expect(response).toStrictEqual(mockUserInformationExtractFromItems)
    })

    test('Error retrieving user info based on department, function queryUserInformationByDepartment', async () => {
        (dynamodb.query().promise as jest.Mock).mockRejectedValueOnce(false)
        const response = await queryUserInformationByDepartment('mockDepartment')
        expect(response).toStrictEqual(false)
    })

    test('Check if user exists in mainTable, function queryUserLogin', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce(mockUserLoginInformation)
        const response = await queryUserLogin('mockEmail','mockPassword')
        expect(response).toStrictEqual(mockUserLoginInformationExtractFromItems)
    })

    test('Error checking if user exists in mainTable, function queryUserLogin', async () => {
        (dynamodb.query().promise as jest.Mock).mockRejectedValueOnce(mockUserLoginInformation)
        const response = await queryUserLogin('mockEmail','mockPassword')
        expect(response).toStrictEqual(false)
    })

    test('Successfully create user attendance, function putNewAttendanceInfo', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce({Items:{}});
        // eslint-disable-next-line no-unexpected-multiline
        (dynamodb.putItem().promise as jest.Mock).mockResolvedValueOnce(true)
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'User attendance successfully created.'
        }
        const response = await putNewAttendanceInfo('mockUserId','mockDate','mockClockIn','mockLocation')
        expect(response).toStrictEqual(matchResponse)
    })

    test('Error creating user attendance, function putNewAttendanceInfo', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce({Items:{}});
        // eslint-disable-next-line no-unexpected-multiline
        (dynamodb.putItem().promise as jest.Mock).mockRejectedValueOnce(false)
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'User attendance had failed to be created.'
        }
        const response = await putNewAttendanceInfo('mockUserId','mockDate','mockClockIn','mockLocation')
        expect(response).toStrictEqual(matchResponse)
    })

    test('User already checked in, function putNewAttendanceInfo', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce(mockAttendanceInformation);
        // eslint-disable-next-line no-unexpected-multiline
        (dynamodb.putItem().promise as jest.Mock).mockResolvedValueOnce(true)
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'Already checked in for the day'
        }
        const response = await putNewAttendanceInfo('mockUserId','mockDate','mockClockIn','mockLocation')
        expect(response).toStrictEqual(matchResponse)
    })

    test('Successfully update user attendance, function putExistingAttendanceInfo', async () => {
        (dynamodb.updateItem().promise as jest.Mock).mockResolvedValueOnce(true)
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'User clock out attendance successfully updated.'
        }
        const response = await putExistingAttendanceInfo('mockUserId','mockDate','mockClockOut')
        expect(response).toStrictEqual(matchResponse)
    })

    test('Error updating user attendance, function putExistingAttendanceInfo', async () => {
        (dynamodb.updateItem().promise as jest.Mock).mockRejectedValueOnce(false)
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'User clock out attendance had failed to be updated.'
        }
        const response = await putExistingAttendanceInfo('mockUserId','mockDate','mockClockOut')
        expect(response).toStrictEqual(matchResponse)
    })

    test('Successfully update user information, function putUserInformation', async () => {
        (dynamodb.updateItem().promise as jest.Mock).mockResolvedValueOnce(true)
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'Updated user Information.'
        }
        const response = await putUserInformation(
            'mockUserId','mockDepartment','mockName','mockDateOfBirth','mockPhoneNumber','mockEmail')
        expect(response).toStrictEqual(matchResponse)
    })

    test('Error updating user information, function putUserInformation', async () => {
        (dynamodb.updateItem().promise as jest.Mock).mockRejectedValueOnce(false)
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'Failed to update user information.'
        }
        const response = await putUserInformation(
            'mockUserId','mockDepartment','mockName','mockDateOfBirth','mockPhoneNumber','mockEmail')
        expect(response).toStrictEqual(matchResponse)
    })

    test('Successfully retrieve user information based on User Id, function queryPassword', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce(mockUserLoginInformation)
        const response = await queryPassword('mockUserId')
        expect(response).toStrictEqual(mockUserLoginInformationExtractFromItems)
    })

    test('Error updating user information based on User Id, function queryPassword', async () => {
        (dynamodb.query().promise as jest.Mock).mockRejectedValueOnce(mockUserLoginInformation)
        const response = await queryPassword('mockUserId')
        expect(response).toStrictEqual(false)
    })

    test('Successfully update user password, function putUserPassword', async () => {
        (dynamodb.deleteItem().promise as jest.Mock).mockResolvedValueOnce(true);
        (dynamodb.putItem().promise as jest.Mock).mockResolvedValueOnce(true)
        const response = await putUserPassword('mockPK','mockSK','mockNewPassword','mockUserId')
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'Updated user password.'
        }
        expect(response).toStrictEqual(matchResponse)
    })

    test('Error updating user password, function putUserPassword', async () => {
        (dynamodb.deleteItem().promise as jest.Mock).mockRejectedValueOnce(false);
        (dynamodb.putItem().promise as jest.Mock).mockRejectedValueOnce(false)
        const response = await putUserPassword('mockPK','mockSK','mockNewPassword','mockUserId')
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'Failed to update user password.'
        }
        expect(response).toStrictEqual(matchResponse)
    })

    test('Successfully retrieve user schedule, function queryUserSchedule', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce(mockScheduleInformation)
        const response = await queryUserSchedule('mockPK','mockDate')
        expect(response).toStrictEqual(mockScheduleInformationExtractFromItems)
    })

    test('Error retrieving user schedule, function queryUserSchedule', async () => {
        (dynamodb.query().promise as jest.Mock).mockRejectedValueOnce(false)
        const response = await queryUserSchedule('mockPK','mockDate')
        expect(response).toStrictEqual(false)
    })

    test('Check if user exists based on email, Exist , function queryUserEmail', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce({Count:1})
        const response = await queryUserEmail('mockUserEmail')
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'User Email Found'
        }
        expect(response).toStrictEqual(matchResponse)
    })

    test('Check if user exists based on email, Does not exist , function queryUserEmail', async () => {
        (dynamodb.query().promise as jest.Mock).mockResolvedValueOnce({Count:0})
        const response = await queryUserEmail('mockUserEmail')
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: ''
        }
        expect(response).toStrictEqual(matchResponse)
    })

    test('Error checking if user exists based on email , function queryUserEmail', async () => {
        (dynamodb.query().promise as jest.Mock).mockRejectedValueOnce(false)
        const response = await queryUserEmail('mockUserEmail')
        const matchResponse : resultMessageResponseTypeDatabase = {
            result: false,
            message: 'User Not Found'
        }
        expect(response).toStrictEqual(matchResponse)
    })
})