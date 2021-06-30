package br.com.zup.jefferson.chavepix

import br.com.zup.jefferson.enums.TipoDeChave
import br.com.zup.jefferson.enums.TipoDeConta
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "chave_pix_rest")
class ChavePix(
    @Column(nullable = false)
    val idCliente: UUID,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val tipoDeConta: TipoDeConta?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val tipoDeChave: TipoDeChave?,
    var chavePix: String?
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var pixId : Long? = null

}
