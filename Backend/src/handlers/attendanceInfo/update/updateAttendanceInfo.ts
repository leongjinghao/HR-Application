/* eslint-disable no-console */
import { putExistingAttendanceInfo } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Update User Attendace With Clock Out Timing
 * @param event - User Attendance Details
 * @returns APIGatewayProxyResult
 */
async function updateAttendanceInfo(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    const { userId, date, clockOut } = event.queryStringParameters
    const result = await putExistingAttendanceInfo(
      userId,
      date,
      clockOut
      )
    apiResponse.body = result.message
    log2CloudWatch('updateAttendanceInfo.ts','updateAttendanceInfo',result.message)
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('updateAttendanceInfo.ts','updateAttendanceInfo',err)
  }

  return apiResponse
}
export const handler = updateAttendanceInfo
