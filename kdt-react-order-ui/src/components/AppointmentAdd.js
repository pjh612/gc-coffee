import React from "react";
import axios from "axios";

export function AppointmentAdd({onAdd, appointment, setAppointment,handleDesignerIdInputChanged,handleMenuIdInputChanged,handleCustomerIdInputChanged,handleAppointedAtInputChanged}) {

    const onAddAppointmentSubmit = () => {
        axios.post('http://localhost:8080/api/v1/appointments', {
                designerId: appointment.designerId,
                menuId: appointment.menuId,
                customerId: appointment.customerId,
                appointedAt: appointment.appointedAt,

            }
        ).then(
            response => {
                onAdd(response.data);
                alert("예약이 추가되었습니다.");
            },
            e => {
                alert("서버 장애");

                console.error(e);
            })
    }

    const handleSubmit = (e) => {
        if (appointment.designerId === "" || appointment.menuId === "" || appointment.customerId === "" || appointment.appointedAt === "") {
            alert("입력값을 확인해주세요!")
        } else {
            onAddAppointmentSubmit(appointment);
        }
    }

    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>예약 추가 Form</b></h5>
            </div>
            <hr/>
            <form>
                <div className="mb-3">
                    <label htmlFor="designerId" className="form-label">디자이너 ID</label>
                    <input type="text" className="form-control mb-1" value={appointment.designerId}
                           onChange={handleDesignerIdInputChanged}
                           id="designerId"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="menuId" className="form-label">시술메뉴 ID</label>
                    <input type="text" className="form-control mb-1" value={appointment.menuId}
                           onChange={handleMenuIdInputChanged}
                           id="menuId"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="customerId" className="form-label">회원 ID</label>
                    <input type="text" className="form-control mb-1" value={appointment.customerId}
                           onChange={handleCustomerIdInputChanged}
                           id="customerId"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">예약일</label>
                    <input type="text" className="form-control mb-1" value={appointment.appointedAt}
                           onChange={handleAppointedAtInputChanged}
                           id="appointedAt"/>
                </div>
            </form>

            <button className="btn btn-dark col-12" onClick={handleSubmit}>예약 추가</button>


        </>
    )
}