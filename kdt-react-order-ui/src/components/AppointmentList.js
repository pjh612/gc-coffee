import {Appointment} from "./Appointment";

export function AppointmentList({appointments = [], setAppointments}) {
    const onRemove = id => {
        setAppointments(appointments.filter(a => a.id !== id));
    };
    return <>
        <h5 className="flex-grow-0"><b>예약 목록</b></h5>
        <ul className="list-group appointments">
            {appointments.map(v =>
                <li key={v.appointmentId} className="list-group-item d-flex mt-3">
                    <Appointment {...v} onRemove={onRemove}/>
                </li>
            )}
        </ul>
    </>
}