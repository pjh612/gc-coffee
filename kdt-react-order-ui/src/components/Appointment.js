import React from "react";
import axios from "axios";

export function Appointment(props) {
    const appointmentId = props.appointmentId;
    const designerId = props.designerId;
    const menuId = props.menuId;
    const customerId = props.customerId;
    const designerName = props.designerName;
    const menuName = props.menuName;
    const customerName = props.customerName;
    const appointedAt = props.appointedAt;

    const handleSubmit = (e) => {
        axios({
                url: `http://localhost:8080/api/v1/appointments/` + appointmentId,
                method: 'delete'
            }
        ).then(
            v => {
                alert("예약 정보가 삭제되었습니다.")
                props.onRemove(appointmentId);
            },
            e => {
                alert("서버 장애");
                console.error(e);
            })
    }
    return <>

        <div className="col">
            <div className="row text-muted">{appointmentId}</div>
        </div>
        <div className="col">
            <div className="row text-muted">{designerId}</div>
            <div className="col text-center designerId">{designerName}</div>
        </div>
        <div className="col">
            <div className="row text-muted">{menuId}</div>
            <div className="col text-center menuId">{menuName}</div>
        </div>
            <div className="col">
                <div className="row text-muted">{customerId}</div>
                <div className="col text-center customerId">{customerName}</div>
            </div>
            <div className="col text-center appointedAt">{appointedAt}</div>
            <button className="btn btn-small btn-outline-dark" onClick={handleSubmit}>예약 취소</button>
        </>
        }