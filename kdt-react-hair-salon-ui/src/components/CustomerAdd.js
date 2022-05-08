import React, {useState} from "react";
import axios from "axios";

export function CustomerAdd({onAdd}) {
    const [customer, setCustomer] = useState({
       name:"", email:"", gender:"MAN", birth:""
    });
    const handleNameInputChanged = (e) => setCustomer({...customer, name: e.target.value})
    const handleEmailInputChanged = (e) => setCustomer({...customer, email: e.target.value})
    const handleGenderInputChanged = (e) => setCustomer({...customer, gender: e.target.value})
    const handleBirthInputChanged = (e) => setCustomer({...customer, birth: e.target.value})

    const onAddCustomerSubmit = () => {
        axios.post('http://localhost:8080/api/v1/customers', {
            name: customer.name,
            email: customer.email,
            gender: customer.gender,
            birth: customer.birth,

            }
        ).then(
            v => {alert("회원이 추가되었습니다.");
                onAdd(customer,v.data.id);
            },
            e => {
                alert("서버 장애");

                console.error(e);
            })
    }
    const handleSubmit = (e) => {
        if (customer.name === "" || customer.email ==="" || customer.gender ===""|| customer.birth==="") {
            alert("입력값을 확인해주세요!")
        } else {
            onAddCustomerSubmit(customer);
        }
    }
    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>회원 추가 Form</b></h5>
            </div>
            <hr/>
            <form>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">회원 이름</label>
                    <input type="text" className="form-control mb-1" value={customer.name}
                           onChange={handleNameInputChanged}
                           id="name"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input type="email" className="form-control mb-1" value={customer.email}
                           onChange={handleEmailInputChanged}
                           id="email"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="gender" className="form-label">성별</label>
                    <select className="form-control mb-1" onChange={handleGenderInputChanged}>
                        <option value="MAN">남성</option>
                        <option value="WOMAN">여성</option>
                    </select>
                </div>
                <div className="mb-3">
                    <label htmlFor="birth" className="form-label">생년월일</label>
                    <input type="text" className="form-control mb-1" value={customer.appointedAt}
                           onChange={handleBirthInputChanged}
                           id="birth"/>
                </div>
            </form>

            <button className="btn btn-dark col-12" onClick={handleSubmit}>회원 추가</button>


        </>
    )
}