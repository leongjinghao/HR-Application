/* eslint-disable no-console */
import { handler as createUserLeave } from '../createUserLeave';
import { LambdaResponse } from '../../../utility/responses';
import { mockData } from '../../../utility/dataType';

console.log = jest.fn;
console.error = jest.fn;

let matchResponse: LambdaResponse;

describe('/create/createUserLeave createUserLeave.ts test', () => {
    test('Create User Leave successfully', async () => {
        matchResponse = { statusCode: 200, body: 'User leave successfully created.' };
        const response = await createUserLeave(await mockData(
            {
                "userId" : "abc",
                "startEndDate":"12122222/1232122",
                "leaveType":"MC",
                "approver":"Ali",
                "remarks":"nil",
                "status":"Pending" 
            }
        ));
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode);
        expect(response.body).toStrictEqual(matchResponse.body);
    });

    test('Create User Leave unsuccessfully, event data as undefined', async () => {
        
        matchResponse = { statusCode: 500, body: ' ' };
        const response = await createUserLeave(await mockData(undefined
        ));
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode);
    });
});