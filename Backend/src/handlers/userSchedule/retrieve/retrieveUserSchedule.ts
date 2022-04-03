/* eslint-disable no-console */
import { queryUserSchedule } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Retrieve User Schedule
 * @param event - User Id
 * @returns APIGatewayProxyResult
 */
async function retrieveUserSchedule(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    const { userId, date } = event.queryStringParameters
    const result = await queryUserSchedule(userId, date)
    const response = {UserId: '', Date: '', Schedule: '', WorkLocation: ''}
    if (result.length === 1) {
      response.UserId = result[0].PK.S!
      response.Date = result[0].SK.S!
      response.Schedule = result[0].Schedule.S!
      response.WorkLocation = result[0].WorkLocation.S!
    }
    apiResponse.body = JSON.stringify(response)
    log2CloudWatch('retrieveUserSchedule.ts','retrieveUserSchedule','User schedule successfully retrieve')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('retrieveUserSchedule.ts','retrieveUserSchedule',err)
  }

  return apiResponse
}
export const handler = retrieveUserSchedule
