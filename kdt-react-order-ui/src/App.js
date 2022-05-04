import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import React, {useEffect, useState} from 'react';

import axios from "axios";
import {DesignerList} from "./components/DesignerList";
import {MenuList} from "./components/MenuList";
import {DesignerAdd} from "./components/DesignerAdd";
import {MenuAdd} from "./components/MenuAdd";
import {AppointmentList} from "./components/AppointmentList";
import {AppointmentAdd} from "./components/AppointmentAdd";
import {CustomerList} from "./components/CustomerList";
import {CustomerAdd} from "./components/CustomerAdd";

function App() {
    const [menus, setMenus] = useState([
        {id: 'uuid-1', name: '콜롬비아 커피 1', price: 5000},
        {id: 'uuid-2', name: '콜롬비아 커피 2', price: 5000},
        {id: 'uuid-3', name: '콜롬비아 커피 3', price: 5000}
    ]);
    const [designers, setDesigners] = useState([
        {id: 'uuid-1', name: 'park', position: 'INTERN', joinedAt: ''},
        {id: 'uuid-2', name: 'lee', position: 'INTERN', joinedAt: ''},
        {id: 'uuid-3', name: 'kim', position: 'INTERN', joinedAt: ''},
    ]);

    const [appointments, setAppointments] = useState([
        {appointmentId: 'uuid-1', customerId: '',customerName:'', menuId: '',menuName:'', designerId: '',designerName:'', appointedAt:''},
    ]);

    const [customers, setCustomers] = useState([
        {id: 'uuid-1', name: '', email: '', gender: '', birth:''},
    ]);

    const onMenuAdd = (o,id)=> {
        o = {...o, id : id};
        setMenus(menus.concat(o));
    };
    const onCustomerAdd = (o,id) => {
        o = {...o, id : id};
        setCustomers(customers.concat(o));
    };
    const onDesignerAdd = (o,id) => {
        console.log(id);
        o = {...o, id : id};
        setDesigners(designers.concat(o));

    };
    const onAppointmentAdd = (o,data) => {
        console.log(data);
        o = {...o, appointmentId:data.appointmentId, designerName:data.designerName, menuName: data.menuName, customerName:data.customerName};
        setAppointments(appointments.concat(o));
    };
    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/designers')
            .then(v => setDesigners(v.data));
        axios.get('http://localhost:8080/api/v1/menus')
            .then(v => setMenus(v.data));
        axios.get('http://localhost:8080/api/v1/appointments')
            .then(v => setAppointments(v.data));
        axios.get('http://localhost:8080/api/v1/customers')
            .then(v => setCustomers(v.data));
    }, []);

    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Hello Hair Salon</h1>
            </div>
            <div className="card">
                <div className="row">
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <DesignerList designers={designers} setDesigners={setDesigners}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <DesignerAdd onAdd={onDesignerAdd} />
                    </div>
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <MenuList menus={menus} setMenus={setMenus}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <MenuAdd onAdd={onMenuAdd} />
                    </div>
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <AppointmentList appointments={appointments} setAppointments={setAppointments}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <AppointmentAdd onAdd={onAppointmentAdd} />
                    </div>

                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <CustomerList customers={customers} setCustomers={setCustomers}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <CustomerAdd onAdd={onCustomerAdd}/>
                    </div>

                </div>

            </div>
        </div>
    );
}

export default App;
