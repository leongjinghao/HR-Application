export const mockResultDisplay = {Items: [
    {
      LeaveType: {S:'mockLeaveType'},
      Approver: {S:'mockApprover'},
      Remarks: {S:'mockRemarks'},
      SK: {S:'mockSK'},
      LeaveStatus: {S:'Approved'},
      PK: {S:'mockPK'}
    },
  ],
}

export const mockResultCalendar = {Items: [
  {
    LeaveType: {S:'mockLeaveType'},
    Approver: {S:'mockApprover'},
    Remarks: {S:'mockRemarks'},
    SK: {S:'mockSK'},
    LeaveStatus: {S:'Removed'},
    PK: {S:'mockPK'}
  },
  {
    LeaveType: {S:'mockLeaveType'},
    Approver: {S:'mockApprover'},
    Remarks: {S:'mockRemarks'},
    SK: {S:'mockSK'},
    LeaveStatus: {S:'Approved'},
    PK: {S:'mockPK'}
  },
],
}

module.exports = { mockResultDisplay,mockResultCalendar }