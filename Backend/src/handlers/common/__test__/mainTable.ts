/* eslint-disable no-console */
import { putCreateLeave } from '../mainTable'
import { resultMessageResponseTypeDatabase } from '../../utility/dataType'
import { mockClient } from 'aws-sdk-client-mock'
import { DynamoDBClient } from '@aws-sdk/client-dynamodb'
import { DynamoDBDocumentClient, PutCommand,PutCommandOutput } from '@aws-sdk/lib-dynamodb'

console.log = jest.fn
console.error = jest.fn

const ddbMock = mockClient(DynamoDBDocumentClient)
beforeEach(() => {
    jest.clearAllMocks()
    ddbMock.reset()
  })

describe('/common/mainTable mainTable.unit.ts', () => {
    test('mainTable Successfully insert data into MainTable', async () => {
            //ddbMock.on(PutCommand).resolves(new Promise(test))
            const matchResponse : resultMessageResponseTypeDatabase = {
            result: true,
            message: 'User leave successfully created.'
        }
        const response = await putCreateLeave(
            'abc',
            '12122222/1232122',
            'MC',
            'Ali',
            'nil',
            'Pending')
        expect(response.result).toStrictEqual(matchResponse.result)
        expect(response.message).toStrictEqual(matchResponse.message)
    })
})