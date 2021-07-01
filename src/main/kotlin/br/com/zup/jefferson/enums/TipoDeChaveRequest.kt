package br.com.zup.jefferson.enums


import br.com.zup.jefferson.TipoDeChave
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator

enum class TipoDeChaveRequest(val grpcAttribute: TipoDeChave) {

    CPF (TipoDeChave.CPF){
        override fun validaChave(chavePix: String?): Boolean {
            if(chavePix.isNullOrBlank()){
                return false
            }
            if(!chavePix.matches("^[0-9]{11}\$".toRegex())){
                return false
            }

            return CPFValidator().run {
                initialize(null)
                isValid(chavePix, null)
            }
        }
    },

    EMAIL(TipoDeChave.EMAIL) {
        override fun validaChave(chavePix: String?): Boolean {
            if(chavePix.isNullOrBlank()){
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(chavePix, null)
            }
        }
    },

    NUMERO_CELULAR(TipoDeChave.NUMERO_CELULAR) {
        override fun validaChave(chavePix: String?): Boolean {
            if(chavePix.isNullOrBlank()){
                return false
            }
            return chavePix.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },

    CHAVE_ALEATORIA(TipoDeChave.CHAVE_ALEATORIA) {
        override fun validaChave(chavePix: String?) = chavePix.isNullOrBlank()
    };

    abstract fun validaChave(chavePix: String?) : Boolean
}